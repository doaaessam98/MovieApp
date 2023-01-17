package com.example.movieapp.Screens.search

import androidx.paging.PagingData
import com.example.movieapp.base.ViewState
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val MoviesResult: Flow<PagingData<Movie>>?=null,
    val isLoading: Boolean?=false
):ViewState {
}