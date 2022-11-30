package com.example.movieapp.data.source.remote


import com.example.movieapp.data.source.remote.api.MovieApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService):
    IRemoteDataSource {


    override val movieApiServiceObject: MovieApiService
        get() = movieApiService



}