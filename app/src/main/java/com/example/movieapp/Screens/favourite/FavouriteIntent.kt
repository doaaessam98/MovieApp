package com.example.movieapp.Screens.favourite

import com.example.movieapp.Screens.home.HomeIntent
import com.example.movieapp.Screens.search.SearchIntent
import com.example.movieapp.base.ViewEvent
import com.example.movieapp.models.Movie

sealed class FavouriteIntent: ViewEvent {
    object FetchFavouriteMovies : FavouriteIntent()
    object BackToHome : FavouriteIntent()
    data class SearchInFavourite(val query:String) : FavouriteIntent()
    data class OpenDetails(val movie: Movie?) : FavouriteIntent()
    object   RemoveMovieFromFavourite: FavouriteIntent()

}