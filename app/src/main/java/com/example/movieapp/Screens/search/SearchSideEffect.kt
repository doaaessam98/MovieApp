package com.example.movieapp.Screens.search

import com.example.movieapp.Screens.home.HomeSideEffect
import com.example.movieapp.base.ViewSideEffect



sealed class SearchSideEffect : ViewSideEffect {
    data class ShowLoadDataError(val message:String): SearchSideEffect()
    sealed class NavigationToHome : SearchSideEffect()
}