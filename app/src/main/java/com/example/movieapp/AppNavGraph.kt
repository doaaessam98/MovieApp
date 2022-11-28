package com.example.movieapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.Screens.view.home.HomeScreen

@Composable
fun AppNav(modifier: Modifier,navController:NavHostController){

  NavHost(
      navController = navController,
      startDestination =Screen.HomeScreen.route
  ){
      composable(Screen.HomeScreen.route){
          HomeScreen(modifier = modifier)
      }

  }




}
 sealed class Screen(val route:String){
     object HomeScreen:Screen(route = "home_screen")
     object MovieDetailsScreen:Screen(route = "movie_details_screen")
 }