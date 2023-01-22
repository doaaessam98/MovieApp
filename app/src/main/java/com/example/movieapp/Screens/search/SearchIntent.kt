package com.example.movieapp.Screens.search

import com.example.movieapp.Screens.home.HomeIntent
import com.example.movieapp.base.ViewEvent
import com.example.movieapp.base.ViewState
import com.example.movieapp.models.Movie

sealed class SearchIntent:ViewEvent{
    data class FetchMoviesForSearch(val query:String) : SearchIntent()
    data class MovieSelected(val movie: Movie?) : SearchIntent()
    object BackToHome : SearchIntent()
}
