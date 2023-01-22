package com.example.movieapp.models.upcoming
import com.example.movieapp.models.Movie
import com.google.gson.annotations.SerializedName


data class UpcomingMovieResponse(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)