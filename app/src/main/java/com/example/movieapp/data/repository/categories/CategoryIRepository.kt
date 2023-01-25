package com.example.movieapp.data.repository.categories

import androidx.paging.PagingData
import com.example.movieapp.base.Result
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

interface CategoryIRepository {

     fun getCategoryMovies(genreId:String):Flow<PagingData<Movie>>
     suspend fun getGenres(): Result<Flow<List<Genre>>>
      suspend fun getGenresFromApi()
    fun getSearchCategoryMovie(query: String,categoryId:Int):Flow<PagingData<Movie>>


}