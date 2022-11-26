package com.example.movieapp.models

sealed class ApiQuery{
    object Popular :ApiQuery()
    object TopRated :ApiQuery()


}
