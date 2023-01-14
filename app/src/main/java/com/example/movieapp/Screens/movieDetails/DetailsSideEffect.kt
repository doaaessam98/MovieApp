package com.example.movieapp.Screens.movieDetails

import com.example.movieapp.base.ViewSideEffect

sealed class DetailsSideEffect :ViewSideEffect{

    sealed class NavigationToHome : DetailsSideEffect()
}