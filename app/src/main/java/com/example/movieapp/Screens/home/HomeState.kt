package com.example.movieapp.Screens.home

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.example.movieapp.base.ViewState
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

//data class HomeState {
//
//    data class Movies(val data:Flow<PagingData<Movie>>)
//    data class Error(val errorMessage: String) : HomeState()
//    object Idle :HomeState()
//    object Loading : HomeState()
//}

data class HomeState(
    val popularMovies:Flow<PagingData<Movie>>?=null,
    val trendingMovies: Flow<PagingData<Movie>>?=null,
    val upcomingMovies:Flow<PagingData<Movie>>?=null,
    val isLoading: Boolean?=true,
) : ViewState