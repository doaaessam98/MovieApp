package com.example.movieapp.data.repository.favourite

import androidx.paging.PagingData
import com.example.movieapp.base.Result
import com.example.movieapp.models.Genre
import com.example.movieapp.models.GenreResponse
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

interface FavouriteIRepository {

     suspend fun addToFavourite(id: Int):Result<Int>
     suspend fun removeFromFavourite(id: Int):Result<Int>
     fun getFavouriteMovie():Result<Flow<List<Movie>>>
     fun getSearchFavouriteMovie(query: String):Result<Flow<List<Movie>>>
     suspend fun isFavourite(id:Int):Result<Boolean>





}