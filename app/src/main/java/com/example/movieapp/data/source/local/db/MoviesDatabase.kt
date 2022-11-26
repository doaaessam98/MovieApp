package com.example.movieapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.models.Movie
import com.example.movieapp.models.RemoteKeys

@Database(
    entities = [Movie::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
 abstract class MoviesDatabase : RoomDatabase(){

    abstract fun reposDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}