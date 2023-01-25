package com.example.movieapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.source.local.db.converter.Converters
import com.example.movieapp.data.source.local.db.daos.*
import com.example.movieapp.data.source.local.db.entities.CategoryRemoteKeys
import com.example.movieapp.data.source.local.db.entities.PopularRemoteKeys
import com.example.movieapp.models.Movie
import com.example.movieapp.data.source.local.db.entities.TrendingRemoteKeys
import com.example.movieapp.data.source.local.db.entities.UpcomingRemoteKeys
import com.example.movieapp.models.Genre

@Database(entities = [Movie::class,Genre::class,TrendingRemoteKeys::class,
    PopularRemoteKeys::class,UpcomingRemoteKeys::class,CategoryRemoteKeys::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun upcomingRemoteKeysDao(): UpcomingRemoteKeysDao
    abstract fun trendingRemoteKeysDao():TrendingRemoteKeysDao
    abstract fun popularRemoteKeysDao():PopularRemoteKeysDao
    abstract fun categoryRemoteKeysDao():CategoryRemoteKeysDao

}