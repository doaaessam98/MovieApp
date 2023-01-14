package com.example.movieapp.Screens.movieDetails

import com.example.movieapp.base.ViewEvent
import com.example.movieapp.models.Movie

sealed class DetailsIntent :ViewEvent{
    data class   AddMovieToFavourite(val movie: Movie) : DetailsIntent()
    data class   RemoveMovieToFavourite(val movieId:Int) : DetailsIntent()
    sealed class   OpenMovieVideo(val movie: Movie) : DetailsIntent()
     object  BackToHome : DetailsIntent()
}