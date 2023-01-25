package com.example.movieapp.data.repository.categories

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.*
import com.example.movieapp.Utils.Constants.NETWORK_PAGE_SIZE
import com.example.movieapp.base.Result
import com.example.movieapp.data.paging.*
import com.example.movieapp.data.source.local.ILocalDataSource
import com.example.movieapp.data.source.remote.RemoteDataSource
import com.example.movieapp.models.Genre
import com.example.movieapp.models.GenreResponse
import com.example.movieapp.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class CategoryRepository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ILocalDataSource

    ): CategoryIRepository {



    override fun getCategoryMovies(genreId:String): Flow<PagingData<Movie>> {
        val genreQuery = "%$genreId%"

        val pagingSourceFactory ={
            localDataSource.databaseObject.movieDao().getCategoryMovies(genreQuery)

        }
        @OptIn(ExperimentalPagingApi::class)
        return  Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = CategoryRemoteMediator(
                genreId,
                remoteDataSource.movieApiServiceObject,
                localDataSource.databaseObject
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }
    override suspend fun getGenres(): Result<Flow<List<Genre>>>{
        getGenresFromApi()
        return  try{
            val result = localDataSource.getGenres()
            Result.Success(result)
        }catch (e:Exception){
            Result.Error(e.message!!)
        }
    }

    override suspend fun getGenresFromApi(){
        Log.e(TAG, "getGenresFromApi", )

        withContext(Dispatchers.IO) {
            try {
                val genres =remoteDataSource.getGenres()
                localDataSource.insertAllGenres(genres.genres)
                Log.e(TAG, "getGenresFromApi:${genres.genres.size} ", )
            } catch (ex:Exception) {

            }
        }
    }

    override fun getSearchCategoryMovie(query: String, categoryId: Int): Flow<PagingData<Movie>> {
        val genreId = "%$categoryId%"
        val searchQuery = "%$query%"

        val pagingSourceFactory ={
            localDataSource.databaseObject.movieDao().getSearchCategoryMovies(searchQuery,genreId)

        }
        @OptIn(ExperimentalPagingApi::class)
        return  Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = CategoryRemoteMediator(
                categoryId.toString(),
                remoteDataSource.movieApiServiceObject,
                localDataSource.databaseObject
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}

