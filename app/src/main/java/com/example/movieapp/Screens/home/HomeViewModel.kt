package com.example.movieapp.Screens.home

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
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
    :BaseViewModel<HomeIntent, HomeState, HomeSideEffect>() {

    override fun initialState(): HomeState {
        return HomeState()
    }

    override fun handleEvents(event: HomeIntent) {
        when (event) {
            is HomeIntent.FetchMovies -> {
                getTrendingMovies()
                getPopularMovies()
               getUpcomingMovie()
            }
            is HomeIntent.MovieSelected -> {
              setEffect { HomeSideEffect.Navigation.OpenMovieDetails(movie = event.movie!!) }

            }
            else -> {}
        }

    }

    private fun getUpcomingMovie() {
        viewModelScope.launch {
            repository.getMoviesByType(ApiQuery.Upcoming()).let {response->
                when (response) {
                    is Result.Loading->{
                        setState { copy(isLoading = true) }
                    }
                    is Result.Success -> {
                        setState { copy( upcomingMovies= response.data,isLoading=false) }
                    }
                    is Result.Error -> {
                        setEffect { HomeSideEffect.ShowLoadDataError(message = response.message!!)}


                    }

                }
            }

        }
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            repository.getMoviesByType(ApiQuery.Trending()).let { response->
                when (response) {
                    is Result.Loading->{
                        setState { copy(isLoading = true) }
                    }
                    is Result.Success -> {
                        setState { copy( trendingMovies= response.data,isLoading=false) }
                    }
                    is Result.Error -> {
                        setEffect { HomeSideEffect.ShowLoadDataError(message = response.message!!)}


                    }

                }
            }

        }
    }


    init {
        setEvent(HomeIntent.FetchMovies)

    }
    @SuppressLint("SuspiciousIndentation")
    private fun getPopularMovies() {
        viewModelScope.launch {
            repository.getMoviesByType(ApiQuery.Popular()).let {response->
                when (response) {
                     is Result.Loading->{
                         setState { copy(isLoading = true) }
                     }
                    is Result.Success -> {
                        setState { copy(popularMovies = response.data,isLoading=false) }
                    }
                    is Result.Error -> {
                        setEffect { HomeSideEffect.ShowLoadDataError(message = response.message!!)}


                    }
                } }
        } }

}

