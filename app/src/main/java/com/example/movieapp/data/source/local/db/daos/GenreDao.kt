package com.example.movieapp.data.source.local.db.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)

    @Query("SELECT * FROM genre ORDER BY name ASC")
    fun getGenres():Flow<List<Genre>>







}