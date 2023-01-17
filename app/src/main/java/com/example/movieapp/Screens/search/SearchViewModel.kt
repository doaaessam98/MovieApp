package com.example.movieapp.Screens.search

import androidx.lifecycle.viewModelScope
import com.example.movieapp.Screens.home.HomeSideEffect
import com.example.movieapp.Screens.home.HomeState
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.data.repository.IRepository
import com.example.movieapp.models.ApiQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.movieapp.base.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: IRepository
    ):BaseViewModel<SearchIntent,SearchState,SearchSideEffect>(){

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> =_searchQuery

    override fun initialState(): SearchState {
       return SearchState()
    }

    override fun handleEvents(event: SearchIntent) {
         when(event){
             is SearchIntent.FetchMoviesForSearch->{
                 _searchQuery.value = event.query
                  getSearchMovie(event.query)
             }
             is SearchIntent.MovieSelected->{

             }
         }
    }

    private fun getSearchMovie(query: String) {
     //  viewModelScope.launch {
//           repository.getMoviesByType(ApiQuery.Search(searchQuery = query)).let { result ->
//             when(result){
//                 is Result.Loading->{
//                     setState { copy(isLoading = true) }
//
//                 }
//                 is Result.Success->{
//                     setState { copy(MoviesResult=result.data,isLoading=false) }
//
//                 }
//                 is Result.Error -> {
//                     setEffect { SearchSideEffect.ShowLoadDataError(message = result.message!!)}
//
//
//                 }
//             }
//
//           }
//       }
    }

}