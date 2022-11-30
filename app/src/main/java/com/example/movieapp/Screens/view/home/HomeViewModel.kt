package com.example.movieapp.Screens.view.home

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Screens.intent.HomeIntent
import com.example.movieapp.Screens.sideEfect.HomeSideEffect
import com.example.movieapp.Screens.uiState.HomeState
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.base.Result
import com.example.movieapp.data.repository.IRepository
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IRepository)
    :BaseViewModel<HomeIntent,HomeState,HomeSideEffect>() {

    override fun initialState(): HomeState {
        return HomeState()
    }

    override fun handleEvents(event: HomeIntent) {
        when (event) {
            is HomeIntent.FetchPopularMovies -> {
                getPopularMovies()
            }
            is HomeIntent.FetchTopRateMovies -> {
                getTopRateMovies()
            }
            is HomeIntent.MovieSelected -> {
              setEffect { HomeSideEffect.Navigation.OpenMovieDetails(movie = event.movie!!) }

            }
            else -> {}
        }

    }


    init {
        getPopularMovies()

    }


    private fun navigateToMovieDetails(movie: Movie) {
        TODO("Not yet implemented")
    }

    private fun getTopRateMovies() {
        viewModelScope.launch {

            repository.getMovies(ApiQuery.TopRated).let { response ->

                when (response) {
                    is Result.Loading-> {
                        setState { copy(isLoading=true) }
                    }
                    is Result.Success -> {
                        setState { copy(movies = response.data,isLoading=false) }
                    }
                    is Result.Error -> {
                         setEffect { HomeSideEffect.ShowLoadDataError(message = response.message!!)}
                    }

                }
            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun getPopularMovies() {
        viewModelScope.launch {
            repository.getMovies(ApiQuery.Popular).let {response->
                when (response) {
                     is Result.Loading->{
                         setState { copy(isLoading = true) }
                     }
                    is Result.Success -> {
                        setState { copy(movies = response.data,isLoading=false) }
                    }
                    is Result.Error -> {
                        setEffect { HomeSideEffect.ShowLoadDataError(message = response.message!!)}

                    }

                }
            }

        }
    }

}
