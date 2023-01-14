package com.example.movieapp.Screens.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.Screens.movieDetails.MovieDetailsScreen
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun FavouriteScreen(
    modifier: Modifier,
    navController: NavHostController)
{

    val movie  = Movie(title = "first move details", releaseDate ="2020" , isFav = false,voteAverage = 7.6,overview = "hello helloe hello,hello,hello,hello", video = true)
    val favouriteMovies = listOf<Movie>(movie,movie,movie,movie,movie,movie)

 Box(){
     ConstraintLayout() {
         val (topBox,ContentBox,back, searchIcon) = createRefs()
         val topGuideline = createGuidelineFromTop(60.dp)
         val topGuideline2 = createGuidelineFromTop(80.dp)
         Box(
             modifier.height(150.dp).fillMaxWidth()
                 .background(Color.Blue)
                 .constrainAs(topBox) {
                     top.linkTo(parent.top)
                     start.linkTo(parent.start)

                 })
         {
             IconButton(onClick = {},
                 modifier
                     .align(Alignment.TopEnd)
                    // .padding(end = 16.dp, top = 32.dp)
                     //.size(32.dp)
             ) {
                     Icon(imageVector = Icons.Rounded.Search,
                         tint = Color.White,
                         contentDescription = stringResource(id = R.string.btn_search_fav)
                     )
                 }
             IconButton(onClick = {},
                 modifier
                     .align(Alignment.TopStart)
                     //.padding(start = 16.dp, top = 32.dp)
                    // .size(32.dp)
                 ) {
                 Icon(imageVector = Icons.Rounded.ArrowBack,
                     tint = Color.White,
                     contentDescription = stringResource(id = R.string.btn_back_fav)
                 )
             }
         }
         LazyColumn(modifier.constrainAs(ContentBox){
              top.linkTo(topGuideline)
              start.linkTo(parent.start)
         }){
             items(favouriteMovies){movie->
               GeneralMovieItem(modifier,movie) {

               }
             }
         }

     }
 }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GeneralMovieItem(modifier: Modifier,movie: Movie, onMovieClick: (Movie) -> Unit) {
    var rating: Float? by remember { mutableStateOf(movie?.voteAverage?.toFloat()) }

    Box(){
        ConstraintLayout() {
            val (cardContent,movieImage) = createRefs()
            val topGuideline = createGuidelineFromTop(400.dp)
            val topGuideline2 = createGuidelineFromTop(80.dp)
            Card(onClick = {}, 
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                modifier = modifier.constrainAs(cardContent){
                 top.linkTo(parent.top,32.dp)
                 start.linkTo(parent.start,16.dp)
            }) {
                 Column() {
                     Text(text = movie!!.title,
                         style = MaterialTheme.typography.h6,
                         fontSize = 24.sp,
                         fontWeight = FontWeight.Bold,
                         color = Color.Black,
                         modifier=modifier.padding(8.dp)
                     )
                     Row(modifier = modifier.padding(16.dp)) {
                         Text(
                             text = stringResource(id = R.string.released_in),
                             style = MaterialTheme.typography.h6,
                             color = Color.Blue,
                             fontSize = 24.sp,

                             )
                         Text(
                             text = movie!!.releaseDate,
                             style = MaterialTheme.typography.h6,
                             color = Color.Blue,
                             fontSize = 24.sp,
                             modifier = modifier.padding(start = 8.dp)
                         )
                     }
                     Row(modifier = modifier.padding(16.dp)){
                         Text(
                             modifier = Modifier
                                 .padding(end = 8.dp),
                             fontSize = 24.sp,
                             text = "${movie?.voteAverage}",
                             color = Color.Yellow
                         )
                         (rating?.div(2))?.toFloat().let {
                             RatingBar(
                                 value = rating!!,
                                 config = RatingBarConfig()
                                     .style(RatingBarStyle.HighLighted),
                                 onValueChange = {
                                     rating = it
                                 },
                                 onRatingChanged = {}
                             ) }

                     }
                 }
                
            }

            Box(
                modifier
                    .padding(16.dp)
                    .constrainAs(movieImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(cardContent.bottom,16.dp)
                    })
            {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${Constants.IMAGE_URL}${movie?.posterPath}")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.images),
                    contentDescription = stringResource(R.string.details_image_description),
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .height(100.dp)
                        .width(100.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, heightDp = 700)
@Composable
fun Preview(){
    val movie  = Movie(title = "first move details", releaseDate ="2020" , isFav = false,voteAverage = 7.6,overview = "hello helloe hello,hello,hello,hello", video = true)
    FavouriteScreen(Modifier, rememberNavController())
}