package com.example.movieapp.data.source.local

import com.example.movieapp.data.source.local.db.MoviesDatabase
import com.example.movieapp.models.Favourite
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow


interface ILocalDataSource {
    val databaseObject: MoviesDatabase
    suspend fun addToFavourite(favourite: Favourite):Int
    suspend fun getFavouriteMovie():Flow<List<Movie>>
    suspend fun isFav(id:Int):Boolean
}