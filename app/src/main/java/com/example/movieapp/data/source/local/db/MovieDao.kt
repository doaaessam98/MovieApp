package com.example.movieapp.data.source.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Favourite
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(repos: List<Movie>)

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getSearchMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie WHERE type=:type ORDER BY popularity DESC")
    fun getPopularityMovies(type: String ="popular"): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie WHERE type =:type ORDER BY popularity DESC")
    fun getTrendingMovies(type: String = "trending"): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie WHERE type =:type  ORDER BY voteAverage DESC")
    fun getUpcomingMovies(type: String ="upcoming"): PagingSource<Int, Movie>

    @Query("DELETE FROM movie WHERE type=:type")
     fun clearMovies(type: String)
//    @Query("SELECT * FROM movie ORDER BY voteAverage DESC")
//    fun SearchInMovies(query: String): PagingSource<Int, Movie>
     @Query("UPDATE  movie set isFav = 1 WHERE id =:id")
      suspend fun addFavourite(id:Int):Int

    @Query("SELECT * FROM movie WHERE isFav = 1 ")
    fun getFavMovie(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(fav:Favourite):Long
    @Transaction



//    @Query("SELECT COUNT (id)  FROM  Favourite WHERE id =:id")
//    suspend fun isFavourite(id:Int):Int


    @Query("SELECT EXISTS(SELECT * FROM Favourite WHERE id =:id)")
    suspend fun isFavourite(id:Int):Boolean
}


