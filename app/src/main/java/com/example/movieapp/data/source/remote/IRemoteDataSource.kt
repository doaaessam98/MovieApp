package com.example.movieapp.data.source.remote

import com.example.movieapp.data.source.remote.api.MovieApiService
import com.example.movieapp.models.MovieResponse
import retrofit2.Response

interface IRemoteDataSource {

     val movieApiServiceObject: MovieApiService?
     suspend fun get():Response<MovieResponse>
}



