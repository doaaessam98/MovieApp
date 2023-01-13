package com.example.movieapp.Screens.home

import com.example.movieapp.base.ViewEvent
import com.example.movieapp.models.Movie

sealed class HomeIntent:ViewEvent{
    object FetchMovies : HomeIntent()
    data class FilterByCategory(val category: Category) : HomeIntent()
    data class SearchForMovie(val query: String): HomeIntent()
    data class MovieSelected(val movie: Movie?) : HomeIntent()
}
