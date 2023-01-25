package com.example.movieapp.data.repository.favourite

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

class FavouriteRepository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ILocalDataSource

    ): FavouriteIRepository {


    @SuppressLint("SuspiciousIndentation")
    override suspend fun addToFavourite(id: Int): Result<Int> {
         return try {
             val isAdded =  localDataSource.addToFavourite(id)
                 Result.Success(isAdded)

         }catch (e:Exception){
             Result.Error(e.message!!)
         }

    }

    override suspend fun removeFromFavourite(id: Int): Result<Int> {
        return try {
            val isRemoved =  localDataSource.removeFromFavourite(id)
            Result.Success(isRemoved)

        }catch (e:Exception){
            Result.Error(e.message!!)
        }
    }

    override  fun getFavouriteMovie():Result<Flow<List<Movie>>> {
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



    override fun getSearchFavouriteMovie(query: String): Result<Flow<List<Movie>>> {
        val searchQuery = "%$query%"
        return  try{
            val favourites  = localDataSource.getSearchFavouriteMovie(searchQuery)
            Result.Success(favourites)
        }catch (e:Exception){
            Result.Error(e.message!!)
        }
    }


}

