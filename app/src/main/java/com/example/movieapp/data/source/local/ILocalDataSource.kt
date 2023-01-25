package com.example.movieapp.data.source.local

import com.example.movieapp.data.source.local.db.MoviesDatabase
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow


interface ILocalDataSource {
    val databaseObject: MoviesDatabase
    suspend fun addToFavourite(id: Int):Int
    suspend fun removeFromFavourite(id: Int):Int
    fun getFavouriteMovie():Flow<List<Movie>>
    fun getSearchFavouriteMovie(query: String):Flow<List<Movie>>
    suspend fun isFav(id:Int):Boolean
    fun insertAllGenres(genres:List<Genre>)
    fun  getGenres():Flow<List<Genre>>
}