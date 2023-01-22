package com.example.movieapp.data.source.remote.api

import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieResponse
import com.example.movieapp.models.upcoming.UpcomingMovieResponse
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
    ):MovieResponse

    @GET("trending/movie/day?")
    suspend fun getTrendingMovies(
        @Query("api_key") api_key:String=Constants.API_KEY,
        @Query("page") page:Int,
        @Query("per_page") itemsPerPage: Int
    ): MovieResponse


    @GET("movie/upcoming?")
    suspend fun getUpcoming(
        @Query("api_key")api_key:String=Constants.API_KEY,
        @Query("page") page :Int,
        @Query("per_page") itemsPerPage: Int
    ): MovieResponse


    @GET("movie/{movie_id}?")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String=Constants.API_KEY
    ): Response<Movie>


    @GET("search/movie?")
    suspend fun getMoviesSearch(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int,
        @Query("api_key") api_key: String = Constants.API_KEY
    ): MovieResponse

}

