package com.example.movieapp.Screens.view.movieDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieDetailsScreen(){
     Column(
         Modifier
             .background(color = Color.Red)
             .fillMaxSize()) {
       Text(text = "details ")
     }
}
@Preview()
@Composable
fun Preview(){
    MovieDetailsScreen()
}