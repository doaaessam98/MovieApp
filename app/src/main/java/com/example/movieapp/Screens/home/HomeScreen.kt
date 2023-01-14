package com.example.movieapp.Screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieapp.R
import com.example.movieapp.Screens.LoadingImageShimmer
import com.example.movieapp.Utils.*
import com.example.movieapp.models.Movie
import com.example.movieapp.navigation.HomeNavigationItem
import com.example.movieapp.navigation.Screen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen (
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController:NavHostController)
{
   val moviesState = viewModel.viewState.value
    val trendingMovies = moviesState.trendingMovies?.collectAsLazyPagingItems()
    val popularMovies = moviesState.popularMovies?.collectAsLazyPagingItems()
    val upcomingMovies = moviesState.upcomingMovies?.collectAsLazyPagingItems()
    val isLoading = moviesState.isLoading
    val sideEffect =viewModel.effect
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            ConstraintLayout {
                val (topScreenRef, searchRef) = createRefs()
                Box(modifier = Modifier
                    .background(
                        Color.Blue,
                        shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp)
                    )
                    .height(240.dp)
                    .constrainAs(topScreenRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    ConstraintLayout(modifier.fillMaxWidth()) {
                        SearchBar(modifier = modifier
                            .padding(horizontal = 16.dp)
                            .constrainAs(searchRef) {
                                top.linkTo(parent.top, 24.dp)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            }) {

                        }
                    }


                }

                // trending
                val (trendingList, popularList,upcomingList) = createRefs()
                val topGuideline = createGuidelineFromTop(80.dp)
                val topGuideline2 = createGuidelineFromTop(350.dp)
                val topGuideline3 = createGuidelineFromTop(630.dp)


                Column (
                    modifier.constrainAs(trendingList) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(topGuideline, 16.dp)

                    }){
                    TrendingMovies(modifier,R.string.trending,trendingMovies,isLoading) {movie ->
                        viewModel.setEvent(HomeIntent.MovieSelected(movie = movie)) }
                   }

                // popular

                Column(
                    modifier
                        .padding(16.dp)
                        .constrainAs(popularList) {
                            start.linkTo(parent.start)
                            top.linkTo(topGuideline2,16.dp)

                        }) {
                     MovieList(modifier,R.string.popular,popularMovies,isLoading) {movie->
                       viewModel.setEvent(HomeIntent.MovieSelected(movie = movie))
                    }

                }
               // upcoming
                Column(
                    modifier
                        .padding(16.dp)
                        .constrainAs(upcomingList) {
                            start.linkTo(parent.start)
                            top.linkTo(topGuideline3, 16.dp)

                        }) {
                     MovieList(modifier,R.string.up_coming,upcomingMovies,isLoading) {movie->
                         viewModel.setEvent(HomeIntent.MovieSelected(movie = movie))

                     }
                }


            }}


    LaunchedEffect(Constants.SIDE_EFFECTS_KEY) {
        sideEffect.onEach { effect ->
               when (effect) {
                   is HomeSideEffect.Navigation.OpenMovieDetails -> {
                     navController.currentBackStackEntry?.savedStateHandle?.set(
                           key = Constants.MOVIE_NAVIGATION_KEY,
                           value =effect.movie
                     )
                       navController.navigate(Screen.Details.route)
                     }
                   is HomeSideEffect.ShowLoadDataError->{
                       scaffoldState.snackbarHostState.showSnackbar(
                           message = effect.message,
                           duration = SnackbarDuration.Long
                       )
                   }


            }
        }.collect()
    }

}

@Composable
fun TrendingMovies(
    modifier: Modifier,
    title: Int,
    trendingMovies:LazyPagingItems<Movie>?,
    isLoading: Boolean?,
    onMovieClicked: (Movie) -> Unit
) {

    Text(text = stringResource(title),
        style = MaterialTheme.typography.h5,
        color = Color.White,
     )


    LazyRow() {
        items(trendingMovies!!) { movie ->
            TrendMovieItem(modifier, movie, isLoading, onMovieClicked)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MovieList(
    modifier: Modifier,
    title: Int,
    movies:LazyPagingItems<Movie>?,
    isLoading: Boolean?,
    onMovieClicked: (Movie) -> Unit
) {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.h5,
        color = Color.Blue,
    )
    LazyRow(){
        items(movies!!){ movie->
            MovieItem(modifier, movie!!, isLoading = isLoading,onMovieClicked)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    modifier: Modifier,
    movie: Movie,
    isLoading: Boolean?,
    onMovieClicked: (Movie) -> Unit) {

    Card(
        modifier
        .fillMaxWidth()
        .padding(12.dp)
            .clickable { onMovieClicked.invoke(movie) },
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp)

    ) {

            when(isLoading){
                true->{
                    LoadingImageShimmer(modifier = modifier,200.0,200.0)

                }
                else->{
                    Column() {
                    GlideImage(
                        model = "${Constants.IMAGE_URL}${movie?.posterPath}",
                        contentDescription = "",
                        modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Fit

                    ) }
                }
            }
        }
    }



@Composable
    fun SearchBar(
        modifier: Modifier,
        onSearchClick:()->Unit
    ) {
        TextField(
            value = "",
            onValueChange = {

            },

            modifier = modifier.clickable {
                onSearchClick.invoke()
            }
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
@OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun TrendMovieItem(
    modifier: Modifier,
    movie: Movie?,
    isLoading:Boolean?,
    onMovieClicked:(Movie)-> Unit
) {
        Card(
            modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { onMovieClicked.invoke(movie!!) },
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp)

        ) {
            when(isLoading){
                true->{
                    LoadingImageShimmer(modifier = modifier,200.0,200.0)
                  }
                else->{
                    GlideImage(
                           model = "${Constants.IMAGE_URL}${movie?.posterPath}",
                           contentDescription = "",
                           modifier
                               .height(200.dp)
                               .requiredWidth(300.dp),
                           contentScale = ContentScale.Fit
                       )
                   }
               }
        }
    }



