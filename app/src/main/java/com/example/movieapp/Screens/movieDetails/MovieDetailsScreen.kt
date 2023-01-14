package com.example.movieapp.Screens.movieDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.Screens.LoadingImageShimmer
import com.example.movieapp.Screens.home.HomeSideEffect
import com.example.movieapp.Utils.Constants
import com.example.movieapp.Utils.MovieDetailsScreen1
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import com.example.movieapp.navigation.Screen
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach




@Composable
fun MovieDetailsScreen(
    modifier: Modifier,
    movie: Movie?,
    navHostController: NavHostController,
    viewModel: MovieDetailsViewModel= hiltViewModel()
){
     val sideEffect =viewModel.effect

    val isFav = rememberSaveable{ mutableStateOf(movie?.isFav) }
    var rating: Float? by remember { mutableStateOf(movie?.voteAverage?.toFloat()) }
    val genresState = viewModel.viewState.value
    Box(modifier.background(Color.White)) {
        ConstraintLayout() {
            val (topBox,ContentBox,back, movieImage) = createRefs()
            val topGuideline = createGuidelineFromTop(400.dp)
            val topGuideline2 = createGuidelineFromTop(80.dp)

            Box(modifier.fillMaxWidth().background(
                Color(0xFF44072D)
            ).height(200.dp)
                .constrainAs(topBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                IconButton(onClick = {
                    if(isFav.value==false) {
                        // viewModel.setEvent(DetailsIntent.AddMovieToFavourite(movie = movie!!))
                    }else{
                        // viewModel.setEvent(DetailsIntent.RemoveMovieToFavourite(movie!!.id))
                    }
                    isFav.value=!isFav.value!!
                },
                    modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp,top=32.dp).size(32.dp)) {
                    if(isFav.value==true){
                        Icon(imageVector = Icons.Rounded.Favorite ,
                            tint = Color.Red ,
                            contentDescription = stringResource(id = R.string.btn_fav)
                        )
                    }else{
                        Icon(imageVector = Icons.Outlined.Favorite,
                            tint = Color.White,
                            contentDescription = stringResource(id = R.string.btn_fav)
                        )
                    }

                }
                IconButton(onClick = {
                    // viewModel.setEvent(DetailsIntent.BackToHome)
                },
                    modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp,top=32.dp).size(32.dp)) {
                    Icon(imageVector = Icons.Rounded.ArrowBack,
                        tint = Color.White,
                        contentDescription = stringResource(id = R.string.btn_back)
                    )
                }
            }

            Box(modifier.padding(16.dp).constrainAs(movieImage) {
                top.linkTo(topGuideline2)
                start.linkTo(parent.start,16.dp)
                end.linkTo(parent.end,16.dp)
            }) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${Constants.IMAGE_URL}${movie?.posterPath}")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.images),
                    contentDescription = stringResource(R.string.details_image_description),
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )
                if(movie!!.video){
                    IconButton(onClick = {},
                        modifier
                            .align(Alignment.Center)
                            .size(48.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    ) {
                        Icon(imageVector = Icons.Outlined.PlayArrow,
                            tint = Color.Red,
                            modifier = modifier.size(32.dp) ,
                            contentDescription = stringResource(id = R.string.btn_play)
                        )
                    }
                }

            }
            Column(modifier.background(Color.White).constrainAs(ContentBox) {
                top.linkTo(movieImage.bottom,16.dp)
                start.linkTo(parent.start,16.dp)
                end.linkTo(parent.end,16.dp)
            }) {

                Text(text = movie!!.title,
                    style = MaterialTheme.typography.h6,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier=modifier.padding(16.dp)
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
//        genresState.let {
//            when{
//                it.loading->{
//                    GenresLoading(modifier,movie.genreIds)
//                }
//                else->{
//                    MovieGenres(modifier = modifier, genres = genresState.genres!!)
//                }
//            }
//
//        }
                Text(text = movie.overview,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Blue,
                    fontSize = 24.sp,
                    modifier=modifier.padding(16.dp)
                )


            }}
    }

    LaunchedEffect(Constants.SIDE_EFFECTS_KEY) {
//        sideEffect.onEach { effect ->
//            when (effect) {
//                is DetailsSideEffect.NavigationToHome -> {
//                    navHostController.popBackStack()
//
//                }
//
//
//            }
        //   }.collect()
    }


}

@Composable
fun GenresLoading(modifier: Modifier, genres: List<Int>) {
    LazyRow(){
        items(genres){genre->
           LoadingImageShimmer(modifier = modifier, width = 32.0, height =32.0 )
        }
    }
}

@Composable
fun MovieGenres(modifier: Modifier,genres:List<Genre>) {

    LazyRow(){
        items(genres){genre->
            GenreItem(modifier ,genre.name)

        }
    }



}

@Composable
fun GenreItem(modifier:Modifier,name: String) {
    Card (
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.padding(8.dp)

            ){
         Text(text = name,
              fontSize = 16.sp,
             style = MaterialTheme.typography.h5,
             color = Color.Blue,
             modifier = modifier.padding(4.dp)
             )
            }

}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun Preview(){
    val movie  = Movie(title = "first move details", releaseDate ="2020" , isFav = false,voteAverage = 7.6,overview = "hello helloe hello,hello,hello,hello", video = true)
   MovieDetailsScreen(Modifier, movie,rememberNavController())
}