package com.example.movieapp.di


import com.example.movieapp.data.repository.categories.CategoryIRepository
import com.example.movieapp.data.repository.categories.CategoryRepository
import com.example.movieapp.data.repository.favourite.FavouriteIRepository
import com.example.movieapp.data.repository.favourite.FavouriteRepository
import com.example.movieapp.data.repository.movies.IRepository
import com.example.movieapp.data.repository.movies.Repository
import com.example.movieapp.data.source.local.ILocalDataSource
import com.example.movieapp.data.source.local.LocalDataSource
import com.example.movieapp.data.source.remote.IRemoteDataSource
import com.example.movieapp.data.source.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {

    @Binds
    fun provideLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource

    @Binds
    fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource

    @Binds
    fun provideRepository(repository: Repository): IRepository

    @Binds
    fun provideFavouriteRepository(repository: FavouriteRepository): FavouriteIRepository

    @Binds
    fun provideCategoryRepository(repository: CategoryRepository): CategoryIRepository

}