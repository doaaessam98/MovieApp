package com.example.movieapp.data.repository

import androidx.paging.PagingData
import com.example.movieapp.base.Result
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Favourite
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

interface IRepository {

     fun getMoviesByType(query: ApiQuery):Flow<PagingData<Movie>>
     fun getPopularMovies():Flow<PagingData<Movie>>
     fun getTrendMovies():Flow<PagingData<Movie>>
     fun getUpcomingMovies():Flow<PagingData<Movie>>
//fun getMoviesByType(query: ApiQuery):Result<Flow<PagingData<Movie>>>

     suspend fun addToFavourite(favourite: Favourite):Result<Int>
     suspend fun getFavouriteMovie():Result<Flow<List<Movie>>>
     suspend fun isFavourite(id:Int):Result<Boolean>

     fun getSearchMovies(query: String): Flow<PagingData<Movie>>




}