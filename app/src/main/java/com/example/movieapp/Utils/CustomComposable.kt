package com.example.movieapp.Screens

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size


//@Composable
//  fun LoadImageFromNetWork(modifier: Modifier,url:String ,width:Double, height:Double){
//    val painter = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data("https://example.com/image.jpg")
//            .size(Size.ORIGINAL) // Set the target size to load the image at.
//            .build()
//    )
//
//    if (painter.state is AsyncImagePainter.State.Success) {
//    }
//
//    Image(
//        painter = painter,
//        contentDescription = ""
//    )
//
//  }
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
            .background(Color(0xFFB1B0B3).copy(alpha = alpha))
    )


}

//@Composable
//fun GifImage(
//    modifier: Modifier = Modifier,
//) {
//    val context = LocalContext.current
//    val imageLoader = ImageLoader.Builder(context)
//        .components {
//            if (SDK_INT >= 28) {
//                add(ImageDecoderDecoder.Factory())
//            } else {
//                add(GifDecoder.Factory())
//            }
//        }
//        .build()
//    Image(
//        painter = rememberAsyncImagePainter(
//            ImageRequest.Builder(context).data(data = R.drawable.YOUR_GIF_HERE).apply(block = {
//                size(Size.ORIGINAL)
//            }).build(), imageLoader = imageLoader
//        ),
//        contentDescription = null,
//        modifier = modifier.fillMaxWidth(),
//    )
//}








