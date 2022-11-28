package com.example.movieapp.data.source.remote


import android.content.ContentValues.TAG
import android.util.Log
import com.example.movieapp.data.source.remote.api.MovieApiService
import com.example.movieapp.models.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService):
    IRemoteDataSource {


    override val movieApiServiceObject: MovieApiService
        get() = movieApiService



}