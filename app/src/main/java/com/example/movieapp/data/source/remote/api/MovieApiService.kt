package com.example.movieapp.data.source.remote.api

import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular?")
    suspend fun getPopularMovies(
        @Query("api_key")api_key:String =Constants.API_KEY,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): MovieResponse

    @GET("movie/top_rated?")
    suspend  fun getTopRatedMovies(
        @Query("api_key")api_key:String=Constants.API_KEY,
        @Query("page") page :Int,
        @Query("per_page") itemsPerPage: Int
    ): Response<MovieResponse>


    @GET("movie/{movie_id}?")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String=Constants.API_KEY
    ): Response<Movie>


}

