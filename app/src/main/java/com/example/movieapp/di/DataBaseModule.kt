package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.source.local.db.MovieDao
import com.example.movieapp.data.source.local.db.MoviesDatabase
import com.example.movieapp.data.source.local.db.RemoteKeysDao


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
     fun repoDataBase(@ApplicationContext context: Context): MoviesDatabase =
         Room.databaseBuilder(context, MoviesDatabase::class.java,"movie_DB").build()

    @Provides
    @Singleton
    fun provideRepoDataBase(db: MoviesDatabase): MovieDao =db.movieDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDataBase(db: MoviesDatabase): RemoteKeysDao =db.remoteKeysDao()
}


