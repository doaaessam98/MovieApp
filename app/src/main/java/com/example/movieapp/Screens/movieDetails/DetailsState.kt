package com.example.movieapp.Screens.movieDetails

import com.example.movieapp.base.ViewState
import com.example.movieapp.models.Genre

data class DetailsState(
    val loading:Boolean=false,
    val isFav:Boolean=false
):ViewState {
}