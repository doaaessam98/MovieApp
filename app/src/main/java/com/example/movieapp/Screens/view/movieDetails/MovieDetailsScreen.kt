package com.example.movieapp.Screens.view.movieDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.Screens.LoadingImageShimmer
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun MovieDetailsScreen(
    modifier: Modifier,
    navHostController: NavHostController){

    Column() {
       Box() {


       }
    }


}
@Preview()
@Composable
fun Preview(){

    MovieDetailsScreen(Modifier, rememberNavController())
}