package com.example.movieapp.data.source.remote

import androidx.paging.PagingData
import com.example.movieapp.data.source.remote.api.MovieApiService
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.GenreResponse
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRemoteDataSource {

     val movieApiServiceObject: MovieApiService?
     suspend fun getGenres(): GenreResponse
}



