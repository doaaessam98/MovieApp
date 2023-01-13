package com.example.movieapp.Screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage


@Composable
  fun LoadImageFromNetWork(modifier: Modifier, width:Double, height:Double){
      CoilImage(
          imageModel = { "" },
          modifier = modifier,
          loading = {
              LoadingImageShimmer(modifier = modifier, width = width, height = height )
          },
          failure = {
              //Text(text = "image request failed.")
              // ImageBitmap.imageResource(R.drawable.placeholder)

          },
          success = {
              Image(
                  bitmap = it.imageBitmap!!,
                  contentDescription = null,
                  modifier = Modifier
                      .width(128.dp)
                      .height(128.dp)
              )
          })
  }
@SuppressLint("SuspiciousIndentation")
@Composable
fun LoadingImageShimmer(modifier: Modifier, width:Double, height:Double) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000

                0.7f at 500
            },

            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .height(height.dp)
            .width(width.dp)
            .background(Color.LightGray.copy(alpha = alpha))
    )


}





