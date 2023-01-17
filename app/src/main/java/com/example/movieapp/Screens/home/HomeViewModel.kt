package com.example.movieapp.Screens.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.IRepository
import com.example.movieapp.models.ApiQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
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


            }
            is HomeIntent.MovieSelected -> {
              setEffect { HomeSideEffect.Navigation.OpenMovieDetails(movie = event.movie!!) }

            }
            else -> {}
        }

    }

    private suspend fun getAllData(){

        viewModelScope.async {
            HomeState(isLoading = true)
            Log.e(TAG, "getAllData: start", )
           val a=  getTrendingMovies()
            Log.e(TAG, "getAllData: A", )



        }.await()


    }

//    private  fun getUpcomingMovie(): Flow<PagingData<Movie>> {
//        return   repository.getMoviesByType(ApiQuery.Upcoming())
//    }
//
    private  fun getTrendingMovies(){
       repository.getMoviesByType(ApiQuery.Trending()).let {

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

   // @SuppressLint("SuspiciousIndentation")
//    private   fun getPopularMovies(): Flow<PagingData<Movie>> {
//        return  repository.getMoviesByType(ApiQuery.Popular())
//    }


}

