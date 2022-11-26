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

    @Query("DELETE FROM movie")
     fun clearMovies()
    @Query("SELECT * FROM movie ORDER BY voteAverage DESC")
    fun getMovieByTopRated(): PagingSource<Int, Movie>
}