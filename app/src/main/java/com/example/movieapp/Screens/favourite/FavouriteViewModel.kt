package com.example.movieapp.Screens.favourite

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.movieapp.base.Result

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: IRepository
    ):BaseViewModel<FavouriteIntent,FavouriteState,FavouriteSideEffect>() {
    override fun initialState(): FavouriteState {
        return FavouriteState()
    }

    override fun handleEvents(event: FavouriteIntent) {
        when(event){
            is FavouriteIntent.FetchFavouriteMovies->{
                Log.e(TAG, "handleEvents: ", )
                getFavouriteMovies()
            }
            is FavouriteIntent.OpenDetails->{

            }
            is FavouriteIntent.RemoveMovieFromFavourite->{
                removeMoveFromFav(event.movieId)
            }
        }
    }

    private fun getFavouriteMovies() {
        setState { FavouriteState(isLoading = true) }
        viewModelScope.launch {
            repository.getFavouriteMovie() .let {
                when(it){
                    is Result.Loading->{
                        setState { FavouriteState(isLoading = true) }
                    }
                    is Result.Success->{
                        val d = it.data?.collect{its->
                            Log.e(TAG, "getFavouriteMovies: ${its.size}", )
                            setState { FavouriteState(FavouriteMovies=it.data,isLoading = false) }
                        }
                        }

                    is Result.Error->{
                        Log.e(TAG, "getFavouriteMovies: ${it.data}", )


                    }
                }
            }
        }
    }
    init {
        setEvent(FavouriteIntent.FetchFavouriteMovies)

    }

    private fun removeMoveFromFav(movieId: Int) {

    }
}