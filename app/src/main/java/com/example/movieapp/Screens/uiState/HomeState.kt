package com.example.movieapp.Screens.uiState

import androidx.paging.PagingData
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

sealed class HomeState {
    object Idle :HomeState()
    object Loading : HomeState()
    data class Movies(val data:Flow<
            PagingData<Movie>>) : HomeState()
    data class Error(val data: String) : HomeState()





}
