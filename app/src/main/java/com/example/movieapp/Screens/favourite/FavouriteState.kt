package com.example.movieapp.Screens.favourite

import androidx.paging.PagingData
import com.example.movieapp.base.ViewState
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow


data class FavouriteState(
     val FavouriteMovies: Flow<List<Movie>>?=null,
     val isLoading: Boolean?=false,
) : ViewState