package com.example.movieapp.data.source.local

import com.example.movieapp.data.source.local.db.MoviesDatabase


interface ILocalDataSource {
    val databaseObject: MoviesDatabase
}