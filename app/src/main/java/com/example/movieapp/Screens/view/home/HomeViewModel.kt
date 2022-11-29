package com.example.movieapp.Screens.view.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.Screens.intent.HomeIntent
import com.example.movieapp.Screens.uiState.HomeState
import com.example.movieapp.data.repository.IRepository
import com.example.movieapp.models.ApiQuery
import com.example.movieapp.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IRepository):ViewModel() {
    private val intentChannel: Channel<HomeIntent> = Channel()


    private val _popularMoviesState = MutableStateFlow<HomeState>(HomeState.Idle)
    val popularMoviesState: StateFlow<HomeState>
        get() = _popularMoviesState
     init {
         setData()
         handelHomeIntent()
     // getPopularMovies()

        }

    private fun setData() {
        viewModelScope.launch {
            intentChannel.send(HomeIntent.FetchTopRateMovies)
        }
    }


    fun handelHomeIntent(){

      viewModelScope.launch {


          intentChannel.consumeAsFlow().collect{intent->

              when(intent){
                  is HomeIntent.FetchPopularMovies->{

                      getPopularMovies()
                  }
                  is HomeIntent.FetchTopRateMovies->{
                      getTopRateMovies()
                  }
                  is HomeIntent.MovieSelected->{
                         navigateToMovieDetails(intent.movie)
                  }
              }

          }
      }

    }

    private fun navigateToMovieDetails(movie: Movie) {
        TODO("Not yet implemented")
    }

    private fun getTopRateMovies() {
        viewModelScope.launch {
            _popularMoviesState.emit(HomeState.Loading)
            try {
                val result =  repository.getMovies(ApiQuery.TopRated)
                    .cachedIn(viewModelScope)

                _popularMoviesState.emit( HomeState.Movies(result))



            }catch (e:Exception){
                _popularMoviesState.emit(HomeState.Error(e.localizedMessage))
            }


        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun  getPopularMovies(){
     viewModelScope.launch {
         _popularMoviesState.emit(HomeState.Loading)
            try {
             val result =  repository.getMovies(ApiQuery.Popular)
                    .cachedIn(viewModelScope)

                   _popularMoviesState.emit( HomeState.Movies(result))



            }catch (e:Exception){
                _popularMoviesState.emit(HomeState.Error(e.localizedMessage))
            }


       }
     }
   }

