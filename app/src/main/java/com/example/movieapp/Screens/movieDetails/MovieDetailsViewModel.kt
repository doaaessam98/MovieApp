package com.example.movieapp.Screens.movieDetails

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.IRepository
import com.example.movieapp.models.Favourite
import com.example.movieapp.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.movieapp.base.Result
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
                  Log.e(TAG, "handleEvents add: ${event.movie.id}", )
                  addMoveToFavourite(event.movie)
              }
              is DetailsIntent.RemoveMovieToFavourite->{
                  Log.e(TAG, "handleEventsremove: ${event.movieId}", )
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
         val favouriteMovie= Favourite(movie.id)
        viewModelScope.launch {

           repository.addToFavourite(favouriteMovie) .let {
                      when(it){
                          is Result.Loading->{
                              setState { DetailsState(loading=true) }
                          }
                          is Result.Success->{
                              Log.e(TAG, "addMoveToFavourite: ${it.data}", )
                              setState {DetailsState(isFav = true, loading = false) }
                          }
                          is Result.Error->{

                          }

                      }
           }


        }
    }

     fun isFavourite(id: Int) {
        viewModelScope.launch {
            repository.isFavourite(id) .let {
                when(it){
                    is Result.Loading->{
                        setState { DetailsState(loading=true) }
                    }
                    is Result.Success->{
                        Log.e(TAG, "addMoveToFavourite: ${it.data}", )
                        setState {DetailsState(isFav = true, loading = false) }
                    }
                    is Result.Error->{

                    }

                }
            }


        }
    }


    fun getMovieGenres(genreIds: List<Int>?) {

    }
}