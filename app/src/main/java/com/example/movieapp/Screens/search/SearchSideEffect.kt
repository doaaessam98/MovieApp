package com.example.movieapp.Screens.search

import com.example.movieapp.base.ViewSideEffect
import com.example.movieapp.models.Movie


sealed class SearchSideEffect : ViewSideEffect {
    data class ShowLoadDataError(val message:String): SearchSideEffect()
    sealed class Navigation : SearchSideEffect(){
        data class OpenMovieDetails(val movie: Movie): Navigation()
        object   BackToHome: Navigation()
    }
}