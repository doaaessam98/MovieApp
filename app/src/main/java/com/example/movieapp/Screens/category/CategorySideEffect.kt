package com.example.movieapp.Screens.category

import com.example.movieapp.base.ViewSideEffect
import com.example.movieapp.models.Movie



sealed class CategorySideEffect : ViewSideEffect {
    data class ShowLoadDataError(val message:String): CategorySideEffect()
    sealed class Navigation : CategorySideEffect(){
        data class OpenMovieDetails(val movie: Movie): Navigation()
    }
}