package com.example.movieapp.Screens.category

import androidx.paging.PagingData
import com.example.movieapp.base.ViewState
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow


 data class CategoryState(
     val categoryMovies: Flow<PagingData<Movie>>?=null,
     //val genres:Flow<List<Genre>>?=null,
     val isLoading: Boolean=false,
     val isGenreLoading:Boolean = false
) : ViewState
