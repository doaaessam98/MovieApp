package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.source.local.db.MovieDao
import com.example.movieapp.data.source.local.db.MoviesDatabase
import com.example.movieapp.data.source.local.db.daos.*


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Provides
     @Singleton
     fun MovieDataBase(@ApplicationContext context: Context): MoviesDatabase =
         Room.databaseBuilder(context, MoviesDatabase::class.java,"movie_DB").build()

    @Provides
    @Singleton
    fun provideMovieDataBase(db: MoviesDatabase): MovieDao =db.movieDao()
    @Provides
    @Singleton
    fun provideGenreDataBase(db: MoviesDatabase): GenreDao =db.genreDao()

    @Provides
    @Singleton
    fun provideUpcomingRemoteKeysDataBase(db: MoviesDatabase): UpcomingRemoteKeysDao =db.upcomingRemoteKeysDao()

    @Provides
    @Singleton
    fun provideTrendingRemoteKeysDataBase(db: MoviesDatabase): TrendingRemoteKeysDao =db.trendingRemoteKeysDao()
    @Provides
    @Singleton
    fun providePopularRemoteKeysDataBase(db: MoviesDatabase): PopularRemoteKeysDao =db.popularRemoteKeysDao()

    @Provides
    @Singleton
    fun provideCategoryRemoteKeysDataBase(db: MoviesDatabase): CategoryRemoteKeysDao =db.categoryRemoteKeysDao()
}


