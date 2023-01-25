package com.example.movieapp.data.repository.movies

import androidx.paging.PagingData
import com.example.movieapp.base.Result
import com.example.movieapp.models.Genre
import com.example.movieapp.models.GenreResponse
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

interface IRepository {

     fun getPopularMovies():Flow<PagingData<Movie>>
     fun getTrendMovies():Flow<PagingData<Movie>>
     fun getUpcomingMovies():Flow<PagingData<Movie>>
     fun getSearchMovies(query: String): Flow<PagingData<Movie>>





}