package com.example.movieapp.Screens.favourite

import androidx.lifecycle.viewModelScope
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.base.Result
import com.example.movieapp.data.repository.favourite.FavouriteIRepository
import com.example.movieapp.data.repository.movies.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: FavouriteIRepository
    ):BaseViewModel<FavouriteIntent,FavouriteState,FavouriteSideEffect>() {


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    var removeMovieId :Int = 0;

    init {
        setEvent(FavouriteIntent.FetchFavouriteMovies)

    }
    override fun initialState(): FavouriteState {
        return FavouriteState()
    }

    override fun handleEvents(event: FavouriteIntent) {
        when(event){
            is FavouriteIntent.FetchFavouriteMovies->{
                getFavouriteMovies()
            }
            is FavouriteIntent.OpenDetails->{
                setEffect { FavouriteSideEffect.Navigation.OpenMovieDetails(movie = event.movie!!) }
            }
            is FavouriteIntent.RemoveMovieFromFavourite->{
                removeMoveFromFav()
            }
            is FavouriteIntent.SearchInFavourite->{
                onQuerySearchChange(event.query)
            }
            is FavouriteIntent.BackToHome->{
                setEffect { FavouriteSideEffect.Navigation.BackToHome }
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
                        setState { FavouriteState(FavouriteMovies=it.data,isLoading = false) }
                    }
                    is Result.Error->{


                    }
                }
            }
        }
    }


    private fun removeMoveFromFav() {
        viewModelScope.launch {
            repository.removeFromFavourite(removeMovieId).let {
                when(it){
                    is Result.Loading->{
                        setState { FavouriteState(isLoading = true) }
                    }
                    is Result.Success->{
                        setEffect { FavouriteSideEffect.ShowToast("movie removed from favourite") }
                    }
                    is Result.Error->{

                    }
                }
            }
        }
    }


    private fun onQuerySearchChange(newQuery: String){
        if(newQuery!=null){
            _searchQuery.value = newQuery
            getSearchMovie(newQuery)
        }


    }

    private fun getSearchMovie(query: String) {
        viewModelScope.launch {
            repository.getSearchFavouriteMovie(query) .let {
                when(it){
                    is Result.Loading->{
                        setState { FavouriteState(isLoading = true) }
                    }
                    is Result.Success->{
                        setState { FavouriteState(FavouriteMovies=it.data,isLoading = false) }
                    }
                    is Result.Error->{


                    }
                }
            }
            }
        }

}