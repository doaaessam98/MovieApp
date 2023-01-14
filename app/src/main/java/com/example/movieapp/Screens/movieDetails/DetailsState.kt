package com.example.movieapp.Screens.movieDetails

import com.example.movieapp.base.ViewState
import com.example.movieapp.models.Genre

data class DetailsState(
    val genres:List<Genre>?= listOf(),
    val loading:Boolean=false
):ViewState {
}