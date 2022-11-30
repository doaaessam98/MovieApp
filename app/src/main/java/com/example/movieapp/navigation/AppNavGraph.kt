package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.Screens.view.home.HomeScreen
import com.example.movieapp.Screens.view.movieDetails.MovieDetailsScreen

@Composable
fun AppNav(modifier: Modifier,navController:NavHostController){

  NavHost(
      navController = navController,
      startDestination =Screen.HomeScreen.route
  ){
      composable(Screen.HomeScreen.route){
          HomeScreen(modifier = modifier,navController=navController)
      }
      composable(Screen.MovieDetailsScreen.route){
          MovieDetailsScreen()
      }

  }




}
 sealed class Screen(val route:String){
     object HomeScreen:Screen(route = "home_screen")
     object MovieDetailsScreen:Screen(route = "movie_details_screen")
 }