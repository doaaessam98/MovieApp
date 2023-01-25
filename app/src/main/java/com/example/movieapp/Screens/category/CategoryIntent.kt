package com.example.movieapp.Screens.category

import com.example.movieapp.base.ViewEvent
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie

sealed class CategoryIntent: ViewEvent {

    data class FetchMovies(val categoryId:Int) : CategoryIntent()
    object FetchGenre:CategoryIntent()
    object BackToHome : CategoryIntent()

    data class SearchInCategory(val query:String?=null, val category:Genre?=null) : CategoryIntent()
    data class OpenDetails(val movie: Movie?) : CategoryIntent()
}