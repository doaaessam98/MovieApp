package com.example.movieapp.Screens.movieDetails

import com.example.movieapp.Screens.favourite.FavouriteSideEffect
import com.example.movieapp.base.ViewSideEffect

sealed class DetailsSideEffect :ViewSideEffect{
    data class ShowToast(val message:String): DetailsSideEffect()

    sealed class Navigation : DetailsSideEffect(){
        object Back: Navigation()
    }
}