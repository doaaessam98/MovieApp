package com.example.movieapp.data.source.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.models.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(repos: List<Movie>)


    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getMovieByPopularity(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie WHERE type =:type ORDER BY voteCount DESC")
    fun getMoviesByType(type: String): PagingSource<Int, Movie>
//    @Query("SELECT * FROM movie WHERE   ")
//    fun getMovieByCategory(generId: Int): PagingSource<Int, Movie>
@Query("SELECT * FROM movie  ORDER BY voteAverage DESC")
fun getMoviesByType1(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
     fun clearMovies()
//    @Query("SELECT * FROM movie WHERE ca")
//    fun FilterDataByCategory(category: Category): PagingSource<Int, Movie>

//    @Query("SELECT * FROM movie ORDER BY voteAverage DESC")
//    fun SearchInMovies(query: String): PagingSource<Int, Movie>




}