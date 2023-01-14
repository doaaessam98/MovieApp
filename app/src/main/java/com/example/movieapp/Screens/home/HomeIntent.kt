package com.example.movieapp.Screens.home

import com.example.movieapp.base.ViewEvent
import com.example.movieapp.models.Movie

sealed class HomeIntent:ViewEvent{
    object FetchMovies : HomeIntent()
    object OpenSearchForMovie: HomeIntent()
    data class MovieSelected(val movie: Movie?) : HomeIntent()
}
