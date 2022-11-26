package com.example.movieapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.Utils.Constants.NETWORK_PAGE_SIZE
import com.example.movieapp.data.paging.MovieRemoteMediator
import com.example.movieapp.data.source.local.ILocalDataSource
import com.example.movieapp.data.source.remote.RemoteDataSource

import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ILocalDataSource

    ): IRepository {

    override fun getMovies(query: ApiQuery): Flow<PagingData<Movie>> {

        val pagingSourceFactory ={
            when (query) {
                is ApiQuery.Popular -> localDataSource.databaseObject.reposDao().getMovieByPopularity()
                is ApiQuery.TopRated -> localDataSource.databaseObject.reposDao().getMovieByTopRated()

            }
    }


        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(
                query,
                remoteDataSource.movieApiServiceObject,
                localDataSource.databaseObject
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow


    }


}

