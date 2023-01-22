package com.example.movieapp.Screens.home

import com.example.movieapp.base.ViewSideEffect
import com.example.movieapp.models.Movie

sealed class HomeSideEffect :ViewSideEffect{
  data class ShowLoadDataError(val message:String): HomeSideEffect()
   sealed class Navigation : HomeSideEffect(){
      data class OpenMovieDetails(val movie: Movie): Navigation()
       object   OpenSearch: Navigation()
   }
}