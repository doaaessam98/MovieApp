package com.example.movieapp.models



import com.google.gson.annotations.SerializedName





data class MovieResponse(
    @SerializedName("page")
    val page: Int=0,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int=0,
    @SerializedName("total_results")
    val totalResults: Int=0

)

//data class MoviesResponse(
//    @SerializedName("page")
//    val page: Int = 0,
//    @SerializedName("results")
//    val results: ArrayList<Result> = arrayListOf(),
//    @SerializedName("total_pages")
//    val totalPages: Int = 0,
//    @SerializedName("total_results")
//    val totalResults: Int = 0
//): java.io.Serializable