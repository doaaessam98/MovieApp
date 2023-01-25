package com.example.movieapp.data.source.local

import com.example.movieapp.data.source.local.db.MoviesDatabase
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


 class LocalDataSource @Inject constructor(private val database: MoviesDatabase): ILocalDataSource {

    override val databaseObject: MoviesDatabase
        get() = database

    override suspend fun addToFavourite(id: Int):Int {
      return  databaseObject.movieDao().addFavourite(id)
    }


    override  fun getFavouriteMovie(): Flow<List<Movie>> {
        return database.movieDao().getFavMovie()
    }

     override suspend fun removeFromFavourite(id: Int): Int {
         return database.movieDao().removeFromFavourite(id)
     }

     override suspend fun isFav(id: Int): Boolean {
         return database.movieDao().isFavourite(id)
     }

     override fun getSearchFavouriteMovie(query: String): Flow<List<Movie>> {
         return database.movieDao().getSearchFavMovie(query)
     }

     override fun insertAllGenres(genres: List<Genre>) {
         database.genreDao().insertAll(genres)
     }

     override fun getGenres(): Flow<List<Genre>> {
         return database.genreDao().getGenres()
     }
 }