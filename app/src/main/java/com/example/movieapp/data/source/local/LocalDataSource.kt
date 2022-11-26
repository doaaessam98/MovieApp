package com.example.movieapp.data.source.local

import com.example.movieapp.data.source.local.db.MoviesDatabase
import javax.inject.Inject


class LocalDataSource @Inject constructor(private val database: MoviesDatabase): ILocalDataSource {


    override val databaseObject: MoviesDatabase
        get() = database


}