package com.example.movieapp.Screens.favourite

import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: IRepository
    ):BaseViewModel<FavouriteIntent,FavouriteState,FavouriteSideEffect>() {
    override fun initialState(): FavouriteState {
        return FavouriteState()
    }

    override fun handleEvents(event: FavouriteIntent) {
        when(event){
            is FavouriteIntent.OpenDetails->{

            }
            is FavouriteIntent.RemoveMovieFromFavourite->{
                removeMoveFromFav(event.movieId)
            }
        }
    }

    private fun removeMoveFromFav(movieId: Int) {

    }
}