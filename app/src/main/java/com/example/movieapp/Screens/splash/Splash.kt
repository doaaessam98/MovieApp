package com.example.movieapp.Screens.splash

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movieapp.R
import com.example.movieapp.Screens.favourite.FavouriteSideEffect
import com.example.movieapp.Utils.Constants
import com.example.movieapp.navigation.HomeNavigationItem
import com.example.movieapp.navigation.Screen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
fun SplashScreen(
    navController: NavHostController,
   ) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(64.dp)
            .background(Color.White)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)


        LottieAnimation(

            composition = composition,
            progress = { logoAnimationState.progress
            },


        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            LaunchedEffect(Constants.SIDE_EFFECTS_KEY) {
                navController.navigate(HomeNavigationItem.Home.route){
                    popUpTo(Screen.Splash.route){
                        inclusive=true
                    }
                }

            }

        }
    }
}

@Preview
@Composable
fun SplashPreview(){

}



/**
 *
 *
 *
 *
 *
 *
 *
 * */