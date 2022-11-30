package com.example.movieapp.Screens.uiState

import androidx.paging.PagingData
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
    val movies:Flow<PagingData<Movie>>?=null,
    val isLoading: Boolean?=false,
) : ViewState