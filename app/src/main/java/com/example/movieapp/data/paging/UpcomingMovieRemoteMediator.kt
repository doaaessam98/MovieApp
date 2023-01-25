package com.example.movieapp.data.paging

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.example.movieapp.Utils.Constants.MOVIES_STARTING_PAGE_INDEX
import com.example.movieapp.data.source.local.db.MoviesDatabase
import com.example.movieapp.data.source.local.db.entities.UpcomingRemoteKeys
import com.example.movieapp.data.source.remote.api.MovieApiService
import com.example.movieapp.models.*

import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UpcomingMovieRemoteMediator(
    private val service:MovieApiService,
    private val moviesDatabase: MoviesDatabase

) : RemoteMediator<Int, Movie>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MOVIES_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = service.getUpcoming(page = page, itemsPerPage = state.config.pageSize)

            val movies = response.results
            val endOfPaginationReached = movies.isEmpty()
             moviesDatabase.withTransaction {
                 if (loadType == LoadType.REFRESH) {
                       moviesDatabase.upcomingRemoteKeysDao().clearRemoteKeys()
                       moviesDatabase.movieDao().clearUpcomingMovies()
                       moviesDatabase.movieDao().clearMovies()
                 }
                 val prevKey = if (page == MOVIES_STARTING_PAGE_INDEX) null else page - 1
                 val nextKey = if (endOfPaginationReached) null else page + 1
                 val keys = movies.map { movie->
                     UpcomingRemoteKeys(MovieId = movie.id, prevKey = prevKey, nextKey = nextKey)
                 }
                 moviesDatabase.upcomingRemoteKeysDao().insertAll(keys)
                 movies.forEach{
                       val movie = moviesDatabase.movieDao().isPopular(it.id)
                     if(movie!=null) {
                         it.isTrending = movie.isTrending
                         it.isPopular = movie.isPopular
                         it.isFav = movie.isFav

                     }
                      it.isUpcoming = true



                 }
                 moviesDatabase.movieDao().insertAll(movies)
                 Log.e(TAG, "load  movie data upcoming: ${page}.......${movies.size}", )


             }
            return MediatorResult.Success(endOfPaginationReached= endOfPaginationReached)
        } catch (exception: IOException) {
            return RemoteMediator.MediatorResult.Error(exception)

        } catch (exception: HttpException) {
            return RemoteMediator.MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): UpcomingRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                moviesDatabase.upcomingRemoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): UpcomingRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                moviesDatabase.upcomingRemoteKeysDao().remoteKeysMovieId(movie.id)
            }

    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): UpcomingRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                moviesDatabase.upcomingRemoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }


}