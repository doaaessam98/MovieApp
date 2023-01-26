package com.example.movieapp.Screens.category

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Screens.favourite.FavouriteState
import com.example.movieapp.base.BaseViewModel
import com.example.movieapp.base.Result
import com.example.movieapp.data.repository.categories.CategoryIRepository
import com.example.movieapp.models.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryIRepository
 ) :BaseViewModel<CategoryIntent,CategoryState,CategorySideEffect >(){

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _genre = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> = _genre
     var selectedCategory:Genre?=null

    init {
        setEvent(CategoryIntent.FetchGenre)
    }
    override fun initialState(): CategoryState {
        return CategoryState()
    }

    override fun handleEvents(event: CategoryIntent) {
        when(event){
            is CategoryIntent.FetchMovies->{
               getCategoryMovies(event.categoryId)
            }
            is CategoryIntent.OpenDetails->{
                setEffect { CategorySideEffect.Navigation.OpenMovieDetails(movie = event.movie!!) }

            }
            is CategoryIntent.SearchInCategory->{
                val query =event.query?:""
                 if(event.query==null){
                     getCategoryMovies(selectedCategory!!.id)
                  }else {
                     onQuerySearchChange(event.query, selectedCategory!!)
                 }
            }
            is CategoryIntent.FetchGenre->{
                 getGenres()
            }
        }
    }

    private fun getCategoryMovies(categoryId: Int) {
         repository.getCategoryMovies(categoryId.toString()).let {
             setState { CategoryState(categoryMovies=it) }
         }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun getGenres() {
        viewModelScope.launch {
            repository.getGenres().let {
                when(it){
                    is Result.Loading->{
                        setState { CategoryState(isLoading = true) }
                    }
                    is Result.Success->{

                     it.data?.collect{genres->
                         _genre.value=genres
                         selectedCategory=genres[0]
                        //setState { CategoryState(genres=it.data,isLoading = false) }
                            getCategoryMovies(genres[0].id)
                        }

                    }
                    is Result.Error->{


                    }
                }
            }
        }
    }

    private fun onQuerySearchChange(newQuery: String,category:Genre){
        if(newQuery!=null){
            _searchQuery.value = newQuery
            getSearchMovie(newQuery,category)
        }


    }

    private fun getSearchMovie(query: String, category: Genre) {
        viewModelScope.launch {
            repository.getSearchCategoryMovie(query,category.id) .let {
                setState { CategoryState(categoryMovies = it) }
                }
            }
        }



}