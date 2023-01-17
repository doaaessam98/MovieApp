package com.example.movieapp.Screens.favourite

import com.example.movieapp.base.ViewEvent
import com.example.movieapp.models.Movie

sealed class FavouriteIntent: ViewEvent {
    data class OpenDetails(val movie: Movie?) : FavouriteIntent()
    data class   RemoveMovieFromFavourite(val movieId:Int) : FavouriteIntent()

}