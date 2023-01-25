package com.example.movieapp.Screens.movieDetails

import android.content.ContentValues.TAG
import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Screens.favourite.FavouriteSideEffect
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.movies.IRepository
import com.example.movieapp.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.movieapp.base.Result
import com.example.movieapp.data.repository.favourite.FavouriteIRepository

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val favRepository:FavouriteIRepository
):
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
                    setEffect { DetailsSideEffect.Navigation.Back }
              }
              is DetailsIntent.OpenMovieVideo->{

              }
              is DetailsIntent.CheckIsFav->{
                  isFavourite(event.id)
              }
              is DetailsIntent.GetGenres->{
                  getMovieGenres(event.genresIds)
              }
          }
    }

    private fun removeMovieFromFavourite(movieId: Int) {
        viewModelScope.launch {
            favRepository.removeFromFavourite(movieId).let {
                when(it){
                    is Result.Loading->{
                        setState { DetailsState(loading=true) }
                    }
                    is Result.Success->{
                        setState {DetailsState(isFav = false, loading = false) }
                        setEffect { DetailsSideEffect.ShowToast("movie removed from favourite") }

                    }
                    is Result.Error->{

                    }

                }
            }
        }
    }

    private fun addMoveToFavourite(movie: Movie) {
        viewModelScope.launch {
            favRepository.addToFavourite(movie.id).let {
                      when(it){
                          is Result.Loading->{
                              setState { DetailsState(loading=true) }
                          }
                          is Result.Success->{
                              setState {DetailsState(isFav = true, loading = false) }
                              setEffect { DetailsSideEffect.ShowToast("movie added to favourite") }

                          }
                          is Result.Error->{

                          }

                      }
           }
        }
    }

     fun isFavourite(id: Int) {
        viewModelScope.launch {
            favRepository.isFavourite(id) .let {
                when(it){
                    is Result.Loading->{
                        setState { DetailsState(loading=true) }
                    }
                    is Result.Success->{
                        Log.e(TAG, "isFavourite: ${it.data}", )
                       setState {DetailsState(isFav = it.data!!, loading = false) }
                    }
                    is Result.Error->{

                    }

                }
            }


        }
    }
    private fun getMovieGenres(genreIds: List<Int>?) {

    }
}