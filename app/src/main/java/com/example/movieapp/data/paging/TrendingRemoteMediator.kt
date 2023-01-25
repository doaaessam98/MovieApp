package com.example.movieapp.data.paging

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapp.Utils.Constants
import com.example.movieapp.data.source.local.db.MoviesDatabase
import com.example.movieapp.data.source.local.db.entities.TrendingRemoteKeys
import com.example.movieapp.data.source.remote.api.MovieApiService
import com.example.movieapp.models.*
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TrendingRemoteMediator(
private val service: MovieApiService,
private val moviesDatabase: MoviesDatabase

) : RemoteMediator<Int, Movie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): RemoteMediator.MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Constants.MOVIES_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                val prevKey = remoteKeys?.prevKey ?: return RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = remoteKeys!=null
                )

                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return RemoteMediator.MediatorResult.Success(
                    endOfPaginationReached = remoteKeys!=null
                )
                nextKey
            }
        }

        try {
            val  response = service.getTrendingMovies(page = page, itemsPerPage = state.config.pageSize)

            val movies = response.results
            val endOfPaginationReached = movies.isEmpty()
            moviesDatabase.withTransaction {
                if(loadType==LoadType.REFRESH) {
                    moviesDatabase.trendingRemoteKeysDao().clearRemoteKeys()
                    moviesDatabase.movieDao().clearTrendingMovies()
                    moviesDatabase.movieDao().clearMovies()

                }
                val prevKey = if(page==Constants.MOVIES_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1
                val keys = movies.map { movie ->
                    TrendingRemoteKeys(MovieId = movie.id, prevKey = prevKey, nextKey = nextKey)
                }
                moviesDatabase.trendingRemoteKeysDao().insertAll(keys)
                movies.forEach {
                    it.isTrending =true
                    val movie = moviesDatabase.movieDao().isPopular(it.id)
                    if(movie!=null) {
                        it.isFav = movie.isFav
                        it.isUpcoming = movie.isUpcoming
                        it.isPopular = movie.isPopular

                    }


                }
                moviesDatabase.movieDao().insertAll(movies)
                Log.e(TAG, "load  movie data trnding: ${page}.......${movies.size}",
                )


            }
            return RemoteMediator.MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return RemoteMediator.MediatorResult.Error(exception)

        } catch (exception: HttpException) {
            return RemoteMediator.MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): TrendingRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                moviesDatabase.trendingRemoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): TrendingRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                moviesDatabase.trendingRemoteKeysDao().remoteKeysMovieId(movie.id)
            }

    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): TrendingRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                moviesDatabase.trendingRemoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }




}
