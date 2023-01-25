package com.example.movieapp.Screens.favourite

import com.example.movieapp.base.ViewSideEffect
import com.example.movieapp.models.Movie



sealed class FavouriteSideEffect : ViewSideEffect {
    data class ShowLoadDataError(val message:String): FavouriteSideEffect()
    data class ShowToast(val message:String): FavouriteSideEffect()
    sealed class Navigation : FavouriteSideEffect(){
        data class OpenMovieDetails(val movie: Movie): Navigation()
        object BackToHome: Navigation()
    }
}