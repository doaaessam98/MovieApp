package com.example.movieapp.Screens.home

import android.annotation.SuppressLint
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.movies.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IRepository)
    :BaseViewModel<HomeIntent, HomeState, HomeSideEffect>() {



    init {
        setEvent(HomeIntent.FetchMovies)

    }

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
            is HomeIntent.OpenSearchForMovie->{
                setEffect { HomeSideEffect.Navigation.OpenSearch}
            }
        }

    }


    private  fun getUpcomingMovie(){
        repository.getUpcomingMovies().let {
            setState { copy( upcomingMovies= it,isLoading=false) }
        }
    }

    private  fun getTrendingMovies(){
       repository.getTrendMovies().let {
             setState { copy( trendingMovies= it,isLoading=false) }
       }
    }

    @SuppressLint("SuspiciousIndentation")
    private   fun getPopularMovies() {
          repository.getPopularMovies().let {
            setState { copy( popularMovies= it,isLoading=false) }
        }
    }


}

