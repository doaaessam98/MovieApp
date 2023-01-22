package com.example.movieapp.Screens.search

import androidx.paging.PagingData
import com.example.movieapp.base.ViewState
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class SearchState(
    val MoviesResult: Flow<PagingData<Movie>>?=null,
    val searchQuery:String? =null,
    val isLoading: Boolean?=true
):ViewState {
}