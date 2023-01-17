package com.example.movieapp.Screens.movieDetails

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle


@SuppressLint("SuspiciousIndentation")
@Composable
fun MovieDetailsScreen(
    modifier: Modifier,
    movie: Movie?,
    navHostController: NavHostController,
    viewModel: MovieDetailsViewModel= hiltViewModel()
){
     val sideEffect =viewModel.effect
    Log.e(TAG, "MovieDetailsScreen: ${movie?.overview}", )
    val isFav = rememberSaveable{ mutableStateOf(movie?.isFav) }
    var rating: Float? by remember { mutableStateOf(movie?.voteAverage?.toFloat()) }
    val genresState = viewModel.viewState.value
    Box(
        modifier
            .fillMaxWidth()
            .background(Color.White)
            ) {
        ConstraintLayout() {
            val (topBox, ContentBox, back, movieImage,movieImage1) = createRefs()
            val topGuideline1 = createGuidelineFromTop(40.dp)
            val topGuideline2= createGuidelineFromTop(170.dp)


            Box(
                modifier
                    .fillMaxWidth()
//                    .background(
//                        Color(0xFF44072D)
//                    )
                    .height(300.dp)
                    .constrainAs(topBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${Constants.IMAGE_URL}${movie?.posterPath}")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.images),
                    contentDescription = stringResource(R.string.details_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(300.dp)

                )

                if(movie!!.video) {
                    IconButton(
                        onClick = {},
                        modifier
                            .align(Alignment.Center)
                            .size(48.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PlayArrow,
                            tint = Color.Red,
                            modifier = modifier.size(32.dp),
                            contentDescription = stringResource(id = R.string.btn_play)
                        )
                    }
                }
                IconButton(
                    onClick = {
                        if(isFav.value==false) {
                            viewModel.setEvent(DetailsIntent.AddMovieToFavourite(movie = movie!!))
                        } else {
                            viewModel.setEvent(DetailsIntent.RemoveMovieToFavourite(movie!!.id))
                        }
                        isFav.value = !isFav.value!!
                    },
                    modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp, top = 24.dp)
                        .size(32.dp)
                ) {
                    if(isFav.value==true) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            tint = Color.Red,
                            contentDescription = stringResource(id = R.string.btn_fav)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            tint = Color.White,
                            contentDescription = stringResource(id = R.string.btn_fav)
                        )
                    }

                }
                IconButton(
                    onClick = {
                        viewModel.setEvent(DetailsIntent.BackToHome)
                    },
                    modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 16.dp)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        tint = Color.White,
                        contentDescription = stringResource(id = R.string.btn_back)
                    )
                }

            }




    Box(modifier = modifier
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp)
        .constrainAs(ContentBox) {
            top.linkTo(topGuideline1)
            start.linkTo(parent.start)
        }) {

        Card(
                elevation = 8.dp,
                modifier = modifier.padding(top = 200.dp)

                    ) {

        ConstraintLayout(modifier
                        .padding(top = 16.dp, end = 8.dp, bottom = 32.dp)
                        .background(Color.White),

                       ) {
               val (rate,release,vote,title,genre,overView) = createRefs()
                   Row(modifier = modifier.padding(8.dp).constrainAs(rate){
                         top.linkTo(parent.top,16.dp)
                         end.linkTo(parent.end)
                   }) {

                       (rating?.div(2))?.toFloat().let {
                            RatingBar(
                                value = it!!,
                                config = RatingBarConfig()
                                    .style(RatingBarStyle.HighLighted),
                                onValueChange = {

                                },
                                onRatingChanged = {},
                            )
                        }
                        Text(
                            modifier = modifier
                                .padding(start = 4.dp),
                            fontSize = 16.sp,
                            text = "${movie?.voteAverage}",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                    }

                        Text(
                            text = stringResource(id = R.string.released_in).plus(movie!!.releaseDate),
                            style = MaterialTheme.typography.h6,
                            color = Color.Black,
                            fontSize = 16.sp,
                            modifier = modifier.constrainAs(release){
                                top.linkTo(rate.bottom,8.dp)
                                start.linkTo(rate.start)
                                end.linkTo(parent.end,4.dp)

                            }
                            )

                    Text(
                        text = stringResource(id = R.string.Votes).plus(movie!!.voteCount),
                        style = MaterialTheme.typography.h6,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(8.dp).constrainAs(vote){
                            top.linkTo(release.bottom,8.dp)
                            start.linkTo(release.start)
                        }
                    )

                    Text(
                        text = movie!!.originalTitle,
                        style = MaterialTheme.typography.h6,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier=modifier.constrainAs(title){
                            top.linkTo(vote.bottom,32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }

                    )



//        genresState.let {
//            when{
//                it.loading->{
//                   /// GenresLoading(modifier,movie.genreIds)
//                }
//                else->{
//                    val genres = listOf<Genre>(Genre(1,"Action"),Genre(2,"Derama"),Genre(3,"Carton"))
//                    LazyRow(
//                        modifier=modifier.constrainAs(genre){
//                            top.linkTo(title.bottom,16.dp)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        }
//                    ){
//                        items(genres){genre->
//                            GenreItem(modifier ,genre.name)
//
//                        }
//                    }
//                }
//            }
//
//        }



            val genres = listOf<Genre>(Genre(1,"Action"),Genre(2,"Derama"),Genre(3,"Carton"))
            LazyRow(
                modifier=modifier.constrainAs(genre){
                    top.linkTo(title.bottom,16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ){
                items(genres){genre->
                    GenreItem(modifier ,genre.name)

                }
            }
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = modifier.padding(all =16.dp).constrainAs(overView){
                            top.linkTo(genre.bottom)
                            start.linkTo(parent.start,8.dp)

                        }
                    
                    )


                }
            }


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${Constants.IMAGE_URL}${movie?.posterPath}")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.images),
            contentDescription = stringResource(R.string.details_image_description),
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 170.dp)
                .height(200.dp)
                .width(130.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(4.dp))
        )
        }
    }
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
             modifier = modifier.padding(8.dp)
             )
            }

}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun Preview(){
    val movie  = Movie(title = "first move details", releaseDate ="2020" , isFav = false,voteAverage = 7.6,overview = "hello helloe hello,hello,hello,hello", video = true)
   MovieDetailsScreen(Modifier, movie,rememberNavController())
}