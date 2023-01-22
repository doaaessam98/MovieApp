package com.example.movieapp.data.repository

import android.annotation.SuppressLint
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.Utils.Constants.NETWORK_PAGE_SIZE
import com.example.movieapp.base.Result
import com.example.movieapp.data.paging.MoviePaginationSearchSource
import com.example.movieapp.data.paging.MovieRemoteMediator
import com.example.movieapp.data.source.local.ILocalDataSource
import com.example.movieapp.data.source.remote.RemoteDataSource
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Favourite
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ILocalDataSource

    ): IRepository {
    override fun getMoviesByType(query: ApiQuery): Flow<PagingData<Movie>>{
        val pagingSourceFactory ={
            when (query) {
              is ApiQuery.Popular -> localDataSource.databaseObject.movieDao().getPopularityMovies(query.query)
              is ApiQuery.Trending -> localDataSource.databaseObject.movieDao().getTrendingMovies(query.query)
              is ApiQuery.Upcoming -> localDataSource.databaseObject.movieDao().getUpcomingMovies(query.query)
              is ApiQuery.Search ->localDataSource.databaseObject.movieDao().getSearchMovies()
            }
        }
        @OptIn(ExperimentalPagingApi::class)
        return  Pager(
                    config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                    remoteMediator = MovieRemoteMediator(
                        query,
                        remoteDataSource.movieApiServiceObject,
                        localDataSource.databaseObject
                    ),
                    pagingSourceFactory = pagingSourceFactory
                ).flow


    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory ={
            localDataSource.databaseObject.movieDao().getPopularityMovies()
           }

        @OptIn(ExperimentalPagingApi::class)
        return  Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(
                ApiQuery.Popular(),
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
            remoteMediator = MovieRemoteMediator(
                ApiQuery.Trending(),
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
            remoteMediator = MovieRemoteMediator(
                ApiQuery.Upcoming(),
                remoteDataSource.movieApiServiceObject,
                localDataSource.databaseObject
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun addToFavourite(favourite: Favourite): Result<Int> {
         return try {
             val isAdded =  localDataSource.addToFavourite(favourite)
                 Result.Success(isAdded)

         }catch (e:Exception){
             Result.Error(e.message!!)
         }

    }

    override suspend fun getFavouriteMovie():Result<Flow<List<Movie>>> {
        return  try{
            val favourites  = localDataSource.getFavouriteMovie()
            Result.Success(favourites)
        }catch (e:Exception){
            Result.Error(e.message!!)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun isFavourite(id: Int): Result<Boolean> {
        return try {
            val favourites  = localDataSource.isFav(id)
              Result.Success(favourites)
        }catch (e:Exception){
            Result.Error(e.message!!)
        }
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


    //override fun getMoviesByType(query: ApiQuery): Result<Flow<PagingData<Movie>>> {
//    val pagingSourceFactory ={
//        when (query) {
//            is ApiQuery.Popular -> localDataSource.databaseObject.reposDao().getMoviesByType(query.query)
//            is ApiQuery.Trending -> localDataSource.databaseObject.reposDao().getMoviesByType(query.query)
//            is ApiQuery.Upcoming -> localDataSource.databaseObject.reposDao().getMoviesByType(query.query)
//           is ApiQuery.Search->localDataSource.databaseObject.reposDao().getMoviesByType(query.query)
//        } }
//    @OptIn(ExperimentalPagingApi::class)
//    return  try {
//        val result= Pager(
//            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
//            remoteMediator = MovieRemoteMediator(
//                query,
//                remoteDataSource.movieApiServiceObject,
//                localDataSource.databaseObject
//            ),
//            pagingSourceFactory = pagingSourceFactory
//        ).flow
//        Result.Success(result)
//    }catch (e:Exception){
//        Result.Error(e.localizedMessage)
//    }
//}


}

