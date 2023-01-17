package com.example.movieapp.Screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.Screens.home.HomeSearchBar
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SearchViewModel= hiltViewModel()
)
    {
        var isSearch by rememberSaveable { mutableStateOf(false) }
        val movie  = Movie(title = "first move details", releaseDate ="2020" , isFav = false,voteAverage = 7.6,overview = "hello helloe hello,hello,hello,hello", video = true)
      //  val favouriteMovies = listOf<Movie>(movie,movie,movie,movie,movie,movie)
        val favouriteMovies = viewModel.viewState.value.MoviesResult?.collectAsLazyPagingItems()
        val state =viewModel.viewState.value
         val searchQuery = viewModel.searchQuery.collectAsState().value

        Box(){
            ConstraintLayout {
                val (topBox,ContentBox,back, searchIcon) = createRefs()
                val topGuideline = createGuidelineFromTop(60.dp)
                val topGuideline2 = createGuidelineFromTop(80.dp)
                Box(
                    modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .background(Color.Blue)
                        .constrainAs(topBox) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)

                        })
                {
                    IconButton(onClick = {},
                        modifier
                            .align(Alignment.TopStart)
                            .padding(start = 8.dp, top = 32.dp)
                        ) {
                            Icon(imageVector = Icons.Rounded.ArrowBack,
                                tint = Color.White,
                                contentDescription = stringResource(id = R.string.btn_search_fav)
                            )
                        }
                        SearchBar(modifier = modifier
                            .align(Alignment.TopEnd)
                            .padding(start = 48.dp, top = 16.dp, end = 16.dp),
                            searchInput =searchQuery,
                            onValueChange = {
                                viewModel.setEvent(SearchIntent.FetchMoviesForSearch(it))
                            })
                        }

                    when{
                        state.isLoading!!->{
                             CircularProgressIndicator()
                        }else->{

                        LazyColumn(
                            modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 16.dp)
                                .constrainAs(ContentBox) {
                                    top.linkTo(topGuideline, 16.dp)
                                    start.linkTo(parent.start)
                                }){
                            items(favouriteMovies!!){ movie->
                                GeneralMovieItem(modifier,movie!!) {
                                   viewModel.setEvent(SearchIntent.MovieSelected(movie))
                                }
                            }
                        }
                    }
                }


            }
        }

    }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GeneralMovieItem(
    modifier: Modifier,movie: Movie,
    onMovieClick: () -> Unit,
   ) {
    var rating: Float? by remember { mutableStateOf(movie?.voteAverage?.toFloat()) }

    Box(
        modifier
            .clickable { onMovieClick.invoke() }
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ){
        Card(onClick = {},
            elevation = 4.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 16.dp, top = 32.dp))
        {
            ConstraintLayout(modifier.padding(end = 4.dp)) {
                val (name,releaseYear,rate) = createRefs()
                Text(text = movie.title,
                    style = MaterialTheme.typography.h6,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier= modifier
                        .padding(8.dp)
                        .constrainAs(name) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                )
                Row(modifier = modifier
                    .padding(8.dp)
                    .constrainAs(releaseYear) {
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end)
                    }) {
                    Text(
                        text = stringResource(id = R.string.released_in),
                        style = MaterialTheme.typography.h6,
                        color = Color.Blue,
                        fontSize = 24.sp,

                        )
                    Text(
                        text = movie.releaseDate,
                        style = MaterialTheme.typography.h6,
                        color = Color.Blue,
                        fontSize = 24.sp,
                        modifier = modifier.padding(start = 8.dp)
                    )
                }

                (rating?.div(2))?.toFloat().let {
                    RatingBar(
                        value = rating!!,
                        config = RatingBarConfig()
                            .style(RatingBarStyle.HighLighted),
                        onValueChange = {
                            rating = it
                        },
                        onRatingChanged = {},
                        modifier = modifier
                            .padding(8.dp)
                            .constrainAs(rate) {
                                top.linkTo(releaseYear.bottom)
                                start.linkTo(releaseYear.start)
                            }
                    ) }



            }


        }
        Box(modifier = modifier.align(Alignment.TopStart))
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
                    .height(140.dp)
                    .width(140.dp)
            )
        }
    }
}


@Composable
fun SearchBar(
    modifier: Modifier,
     searchInput: String,
    onValueChange:(String)->Unit

) {
    TextField(
        value = searchInput,
        onValueChange = {
            onValueChange.invoke(it)
        },

        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(50))
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.DarkGray,
                contentDescription = null
            )
        }

    )
}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun SearchPreview(){
    val movie  = Movie(title = "first move details", releaseDate ="2020" , isFav = false,voteAverage = 7.6,overview = "hello helloe hello,hello,hello,hello", video = true)
    SearchScreen(Modifier, rememberNavController())
}