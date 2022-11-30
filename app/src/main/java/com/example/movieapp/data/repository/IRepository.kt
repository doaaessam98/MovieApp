package com.example.movieapp.data.repository

import androidx.paging.PagingData
import com.example.movieapp.base.Result
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

interface IRepository {

     fun getMovies(query: ApiQuery): Result<Flow<PagingData<Movie>>>

}