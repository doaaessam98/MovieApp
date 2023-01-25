package com.example.movieapp.navigation

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.R
import com.example.movieapp.Screens.category.CategoryScreen
import com.example.movieapp.Screens.favourite.FavouriteScreen
import com.example.movieapp.Screens.home.HomeScreen
import com.example.movieapp.Screens.movieDetails.MovieDetailsScreen
import com.example.movieapp.Screens.search.SearchScreen
import com.example.movieapp.Screens.splash.SplashScreen
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun AppNav(modifier:Modifier,navController:NavHostController){
    val systemUiController: SystemUiController = rememberSystemUiController()
    val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
    val tweenSpec = tween<IntOffset>(durationMillis = 2000, easing = CubicBezierEasing(0.08f,0.93f,0.68f,1.27f))
    NavHost(navController = navController,
        startDestination =Screen.Splash.route,
    ){

       composable(route = Screen.Splash.route,
//                enterTransition = { initial, _ ->
//            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
//        },
//            exitTransition = { _, target ->
//                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
//            },
//            popEnterTransition = { initial, _ ->
//                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
//            },
//            popExitTransition = { _, target ->
//                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = springSpec)
//            }


        ){
            SplashScreen(navController = navController)
            systemUiController.isStatusBarVisible = false

        }
        composable(route = HomeNavigationItem.Home.route){
            HomeScreen(modifier = modifier, navController = navController)
            systemUiController.isStatusBarVisible = true
            systemUiController.setSystemBarsColor(color = MaterialTheme.colors.secondary)
        }
        composable(route= HomeNavigationItem.Favourite.route){
            FavouriteScreen(modifier = modifier, navController = navController,)
        }

        composable(route= HomeNavigationItem.Category.route){
            CategoryScreen(modifier = modifier, navController = navController)
        }
        composable(route=Screen.Details.route){
            val movie =navController.previousBackStackEntry?.savedStateHandle?.get<Movie>(
                    key = Constants.MOVIE_NAVIGATION_KEY)
            movie?.let {
                MovieDetailsScreen(modifier = modifier, movie,navController = navController)
            }

           }

        composable(route=Screen.Search.route){
            SearchScreen(modifier = modifier,navController = navController)
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
        object Search:Screen(route = "search_screen")
       object Details:Screen(route = "details_screen")
        object Splash:Screen(route = "splash_screen")

    }


