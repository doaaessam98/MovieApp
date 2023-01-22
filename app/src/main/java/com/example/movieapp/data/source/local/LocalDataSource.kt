package com.example.movieapp.data.source.local

import com.example.movieapp.data.source.local.db.MoviesDatabase
import com.example.movieapp.models.Favourite
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


 class LocalDataSource @Inject constructor(private val database: MoviesDatabase): ILocalDataSource {

    override val databaseObject: MoviesDatabase
        get() = database

    override suspend fun addToFavourite(favourite: Favourite):Int {
      return  databaseObject.movieDao().addFavourite(favourite.id)
    }


    override suspend fun getFavouriteMovie(): Flow<List<Movie>> {
        return database.movieDao().getFavMovie()
    }

     override suspend fun isFav(id: Int): Boolean {
         return database.movieDao().isFavourite(id)
     }
 }