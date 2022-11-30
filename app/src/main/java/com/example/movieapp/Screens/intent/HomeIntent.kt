package com.example.movieapp.Screens.intent

import com.example.movieapp.base.ViewEvent
import com.example.movieapp.models.Movie

sealed class HomeIntent:ViewEvent{
    object FetchPopularMovies : HomeIntent()
    object FetchTopRateMovies : HomeIntent()
    data class SearchForMovie(val query: String): HomeIntent()
    data class MovieSelected(val movie: Movie?) : HomeIntent()
}
