package com.example.movieapp.Screens.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
            is HomeIntent.OpenSearchForMovie->{
                Log.e(TAG, "handleEvents: search", )
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

//    private fun getTrendingMovies() {
//        viewModelScope.launch {
//            repository.getMoviesByType(ApiQuery.Trending()).let { response->
//                when (response) {
//                    is Result.Loading->{
//                        setState { copy(isLoading = true) }
//                    }
//                    is Result.Success -> {
//                        setState { copy( trendingMovies= response.data,isLoading=false) }
//                    }
//                    is Result.Error -> {
//                        Log.e(TAG, "getTrendingMovies: ${response.message}", )
//                        setEffect { HomeSideEffect.ShowLoadDataError(message = response.message!!)}
//
//
//                    }
//
//                }
//            }
//
//        }
//    }


    init {
        setEvent(HomeIntent.FetchMovies)

    }

    @SuppressLint("SuspiciousIndentation")
    private   fun getPopularMovies() {
          repository.getPopularMovies().let {
            setState { copy( popularMovies= it,isLoading=false) }
        }
    }


}

