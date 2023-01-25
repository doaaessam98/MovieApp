package com.example.movieapp.data.repository.movies

import android.annotation.SuppressLint
import androidx.paging.*
import com.example.movieapp.Utils.Constants.NETWORK_PAGE_SIZE
import com.example.movieapp.base.Result
import com.example.movieapp.data.paging.*
import com.example.movieapp.data.repository.movies.IRepository
import com.example.movieapp.data.source.local.ILocalDataSource
import com.example.movieapp.data.source.remote.RemoteDataSource
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ILocalDataSource

    ): IRepository {

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory ={
            localDataSource.databaseObject.movieDao().getPopularityMovies()
           }

        @OptIn(ExperimentalPagingApi::class)
        return  Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = PouplarRemoteMediator(
                remoteDataSource.movieApiServiceObject,
                localDataSource.databaseObject
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override fun getTrendMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory ={
               localDataSource.databaseObject.movieDao().getTrendingMovies() }
        @OptIn(ExperimentalPagingApi::class)
        return  Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = TrendingRemoteMediator(
                remoteDataSource.movieApiServiceObject,
                localDataSource.databaseObject
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory ={
            localDataSource.databaseObject.movieDao().getUpcomingMovies()

        }
        @OptIn(ExperimentalPagingApi::class)
        return  Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = UpcomingMovieRemoteMediator(
                remoteDataSource.movieApiServiceObject,
                localDataSource.databaseObject
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override fun getSearchMovies(
        query: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePaginationSearchSource(remoteDataSource.movieApiServiceObject, query) }
        ).flow
    }



}

