package com.example.movieapp.Screens.movieDetails

import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.IRepository
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private  val repository: IRepository):
    BaseViewModel<DetailsIntent,DetailsState,DetailsSideEffect>(){




    override fun initialState(): DetailsState {
       return DetailsState()
    }

    override fun handleEvents(event: DetailsIntent) {
          when(event){
              is DetailsIntent.AddMovieToFavourite->{
                  addMoveToFavourite(event.movie)
              }
              is DetailsIntent.RemoveMovieToFavourite->{
                  removeMovieFromFavourite(event.movieId)
              }
              is DetailsIntent.BackToHome->{

              }
              is DetailsIntent.OpenMovieVideo->{

              }
          }
    }

    private fun removeMovieFromFavourite(movieId: Int) {

    }

    private fun addMoveToFavourite(movie: Movie) {

    }

    fun getMovieGenres(genreIds: List<Int>?) {

    }
}