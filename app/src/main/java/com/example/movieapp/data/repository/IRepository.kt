package com.example.movieapp.data.repository

import androidx.paging.PagingData
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRepository {

     fun getMovies(query: ApiQuery): Flow<PagingData<Movie>>
     suspend fun get():Response<MovieResponse>
}