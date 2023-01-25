package com.example.movieapp.Screens.search

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.movies.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IRepository
    ):BaseViewModel<SearchIntent,SearchState,SearchSideEffect>(){


    private val _searchBar = MutableStateFlow("")
    val searchBar: StateFlow<String> = _searchBar

  init {
     // setEvent(SearchIntent.FetchMoviesForSearch("a"))
  }
    override fun initialState(): SearchState {
       return SearchState()
    }
    override fun handleEvents(event: SearchIntent) {
         when(event){
             is SearchIntent.FetchMoviesForSearch->{
                  onQueryChange(event.query)
             }
             is SearchIntent.MovieSelected->{
                 setEffect { SearchSideEffect.Navigation.OpenMovieDetails(movie = event.movie!!) }

             }
             is SearchIntent.BackToHome->{
                setEffect { SearchSideEffect.Navigation.BackToHome }
             }

         }
    }

    private fun getSearchMovie(query: String) {
           viewModelScope.launch {
                repository.getSearchMovies(query).cachedIn(viewModelScope).let {
                    setState { SearchState(MoviesResult = it) }
                }
           }
    }

 private fun onQueryChange(newQuery: String){
     if(newQuery!=null){
         _searchBar.value = newQuery
         getSearchMovie(newQuery)
     }


 }

    }

