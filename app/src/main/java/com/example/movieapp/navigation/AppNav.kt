package com.example.movieapp.navigation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.R
import com.example.movieapp.Screens.category.CategoryScreen
import com.example.movieapp.Screens.favourite.FavouriteScreen
import com.example.movieapp.Screens.home.HomeScreen
import com.example.movieapp.Screens.movieDetails.MovieDetailsScreen
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie

@Composable
fun AppNav(modifier:Modifier,navController:NavHostController){
    NavHost(navController = navController,
        startDestination = HomeNavigationItem.Home.route,
    ){

        composable(route = HomeNavigationItem.Home.route){
            HomeScreen(modifier = modifier, navController = navController)
        }
        composable(route= HomeNavigationItem.Favourite.route){
            FavouriteScreen(modifier = modifier, navController = navController,)
        }

        composable(route= HomeNavigationItem.Category.route){
            CategoryScreen(modifier = modifier, navController = navController)
        }
        composable(route=Screen.Details.route){
             var movie:Movie?=null
            LaunchedEffect(key1 =it){
                movie =navController.previousBackStackEntry?.savedStateHandle?.get<Movie>(
                    key = Constants.MOVIE_NAVIGATION_KEY)
            }
            Log.e(TAG, "AppNav: ${movie?.title}", )
              MovieDetailsScreen(modifier = modifier, movie,navHostController = navController)
           }


    }
}

sealed class HomeNavigationItem(
    val route:String,
    @StringRes
    val title:Int,
    val icon: ImageVector,


    ) {
    object Home :
        HomeNavigationItem(route = "home_screen", title = R.string.home, icon = Icons.Rounded.Home)

    object Category :
        HomeNavigationItem(route = "category_screen", R.string.category, Icons.Default.Menu)

    object Favourite : HomeNavigationItem(
        route = "favourite_screen",
        R.string.favourite,
        Icons.Rounded.Favorite.apply {
            tintColor.green
        })

}
    sealed class Screen(val route: String){
       object Details:Screen(route = "details_screen")
        object  Splash:Screen(route = "splash_screen")

    }


