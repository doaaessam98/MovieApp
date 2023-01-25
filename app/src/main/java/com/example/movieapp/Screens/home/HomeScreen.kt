package com.example.movieapp.Screens.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieapp.R
import com.example.movieapp.Screens.LoadingImageShimmer
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
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

    val isLoading by remember { mutableStateOf(moviesState.isLoading) }
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
                        MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(bottomEnd = 12.dp, bottomStart = 12.dp)
                    )
                    .height(240.dp)
                    .constrainAs(topScreenRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    ConstraintLayout(modifier.fillMaxWidth()) {
                        HomeSearchBar(modifier = modifier
                            .padding(horizontal = 16.dp)
                            .constrainAs(searchRef) {
                                top.linkTo(parent.top, 24.dp)
                                end.linkTo(parent.end)
                                start.linkTo(parent.start)
                            }) {
                           viewModel.setEvent(HomeIntent.OpenSearchForMovie)
                        }
                    }


                }

                // trending
                val (trendingList, popularList,upcomingList) = createRefs()
                val topGuideline = createGuidelineFromTop(80.dp)


                Column (
                    modifier.constrainAs(trendingList) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(topGuideline, 16.dp)

                    }){
                    TrendingMovies(modifier,R.string.trending,trendingMovies) {movie ->
                        viewModel.setEvent(HomeIntent.MovieSelected(movie = movie)) }
                   }


                // upcoming
                Column(
                    modifier
                        .padding(end = 16.dp, top = 8.dp)
                        .constrainAs(upcomingList) {
                            start.linkTo(parent.start,16.dp)
                            top.linkTo(trendingList.bottom)

                        }) {
                        UpcomingMovieList(modifier,R.string.up_coming,upcomingMovies) {movie->
                        viewModel.setEvent(HomeIntent.MovieSelected(movie = movie))

                    }
                }
                // popular

                Column(
                    modifier
                        .padding(end=16.dp)
                        .constrainAs(popularList) {
                            start.linkTo(parent.start,16.dp)
                            top.linkTo(upcomingList.bottom,8.dp)

                        }) {
                     PopularMovieList(modifier,R.string.popular,popularMovies) { movie->
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
                       navController.navigate(Screen.Details.route){
                               
                       }
                     }
                   is HomeSideEffect.ShowLoadDataError->{
                       scaffoldState.snackbarHostState.showSnackbar(
                           message = effect.message,
                           duration = SnackbarDuration.Long
                       )
                   }
                   is HomeSideEffect.Navigation.OpenSearch->{
                       Log.e(TAG, "HomeScreen: searc effect", )
                       navController.navigate(Screen.Search.route)
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
    onMovieClicked: (Movie) -> Unit
) {

    Text(text = stringResource(title),
        style = MaterialTheme.typography.h5,
        color = Color.White,
     )

    LazyRow(modifier= modifier.height(200.dp)){
        items(trendingMovies!!) { movie ->
            TrendMovieItem(modifier, movie!!,onMovieClicked)
            Divider()
        }

        val loadState = trendingMovies.loadState.mediator
        item {
            if (loadState?.refresh == LoadState.Loading) {

                Box(
                    modifier = Modifier
                        .fillParentMaxSize(),
                    ) {
                    LoadingImageShimmer(modifier = modifier, width = 300.0, height =200.0 )
                    CircularProgressIndicator(
                        modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.primary)
                }
            }

            if (loadState?.append == LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = Color.Red)
                }
            }

            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                val isPaginatingError = (loadState.append is LoadState.Error) || trendingMovies.itemCount > 1
                val error = if (loadState.append is LoadState.Error)
                    (loadState.append as LoadState.Error).error
                else
                    (loadState.refresh as LoadState.Error).error

                val modifier = if (isPaginatingError) {
                    Modifier.padding(8.dp)
                } else {
                    Modifier.fillParentMaxSize()
                }
                Column(
                    modifier = modifier
                        .fillParentMaxHeight()
                        .padding(top = 32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (!isPaginatingError) {
                        Icon(
                            modifier = Modifier
                                .size(64.dp),
                            imageVector = Icons.Rounded.Warning, contentDescription = null
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = stringResource(id = R.string.loading_error_message),
                        textAlign = TextAlign.Center,
                        color = Color.White,

                    )

                    Button(
                        onClick = {
                            trendingMovies.refresh()
                        },
                        content = {
                            Text(text = "Refresh")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                        )
                    )
                }
            }
        }

    }


    }

@SuppressLint("SuspiciousIndentation")
@Composable
fun PopularMovieList(
    modifier: Modifier,
    title: Int,
    movies:LazyPagingItems<Movie>?,
    onMovieClicked: (Movie) -> Unit
) {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.secondary,
    )
    if(movies!=null) {
        LazyRow(modifier = modifier.padding(top = 8.dp)) {
            items(movies) { movie ->
                PopularMovieItem(modifier, movie!!, onMovieClicked)
            }
            val loadState = movies.loadState.mediator
            item {
                if (loadState?.refresh == LoadState.Loading) {

                    Box(
                        modifier = Modifier
                            .fillParentMaxSize(),
                    ) {
                        LoadingImageShimmer(modifier = modifier
                            .height(200.dp)
                            .fillMaxWidth(), width = 200.0, height =200.0 )
                    }
                }

                if (loadState?.append == LoadState.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = Color.Red)
                    }
                }

                if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                    val isPaginatingError = (loadState.append is LoadState.Error) || movies.itemCount > 1
                    val error = if (loadState.append is LoadState.Error)
                        (loadState.append as LoadState.Error).error
                    else
                        (loadState.refresh as LoadState.Error).error

                    val modifier = if (isPaginatingError) {
                        Modifier.padding(8.dp)
                    } else {
                        Modifier.fillParentMaxSize()
                    }
                    Column(
                        modifier = modifier
                            .fillParentMaxHeight()
                            .padding(top = 32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (!isPaginatingError) {
                            Icon(
                                modifier = Modifier
                                    .size(64.dp),
                                imageVector = Icons.Rounded.Warning, contentDescription = null
                            )
                        }

                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = stringResource(id = R.string.loading_error_message),
                            textAlign = TextAlign.Center,
                            color = Color.White,

                            )

                        Button(
                            onClick = {
                                movies.refresh()
                            },
                            content = {
                                Text(text = "Refresh")
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White,
                            )
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun UpcomingMovieList(
    modifier: Modifier,
    title: Int,
    movies:LazyPagingItems<Movie>?,
    onMovieClicked: (Movie) -> Unit
) {
    Text(
        text = stringResource(id = title),
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.secondary,
    )
    if(movies!=null) {
        LazyRow(modifier.padding(top = 8.dp).height(120.dp)) {
            items(movies) { movie ->
                UpcomingMovieItem(modifier, movie!!, onMovieClicked)
            }
            val loadState = movies.loadState.mediator

            item {
                if (loadState?.refresh == LoadState.Loading) {

                    Row(
                        modifier = Modifier
                            .fillParentMaxSize(),
                    ) {
                        LoadingImageShimmer(modifier = modifier.clip(CircleShape), width = 120.0, height =120.0 )
                        LoadingImageShimmer(modifier = modifier.clip(CircleShape), width = 120.0, height =120.0 )
                        LoadingImageShimmer(modifier = modifier.clip(CircleShape), width = 120.0, height =120.0 )

                    }
                }

                if (loadState?.append == LoadState.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = Color.Red)
                    }
                }

                if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                    val isPaginatingError = (loadState.append is LoadState.Error) || movies.itemCount > 1
                    val error = if (loadState.append is LoadState.Error)
                        (loadState.append as LoadState.Error).error
                    else
                        (loadState.refresh as LoadState.Error).error

                    val modifier = if (isPaginatingError) {
                        Modifier.padding(8.dp)
                    } else {
                        Modifier.fillParentMaxSize()
                    }
                    Column(
                        modifier = modifier
                            .fillParentMaxHeight()
                            .padding(top = 32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (!isPaginatingError) {
                            Icon(
                                modifier = Modifier
                                    .size(64.dp),
                                imageVector = Icons.Rounded.Warning, contentDescription = null
                            )
                        }

                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = stringResource(id = R.string.loading_error_message),
                            textAlign = TextAlign.Center,
                            color = Color.White,

                            )

                        Button(
                            onClick = {
                                movies.refresh()
                            },
                            content = {
                                Text(text = "Refresh")
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White,
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PopularMovieItem(
    modifier: Modifier,
    movie: Movie,
    onMovieClicked: (Movie) -> Unit) {
    var isImageLoading by remember { mutableStateOf(false) }

   Box(modifier =modifier ){

   Card(
       modifier
           .height(200.dp)
           .width(150.dp)
           .padding(end = 16.dp)
           .clickable { onMovieClicked.invoke(movie) },
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp)

    ) {
       if(movie.posterPath!=null) {


           val painter = rememberAsyncImagePainter(
               model = "${Constants.IMAGE_URL}${movie?.posterPath}",
           )

           isImageLoading = when (painter.state) {
               is AsyncImagePainter.State.Loading -> true
               else -> false
           }


           Image(
               modifier = Modifier
                   .height(200.dp)
                   .fillMaxWidth(),
               painter = painter,
               contentDescription = "Poster Image",
               contentScale = ContentScale.FillBounds,
           )


       }
   }
       if (isImageLoading) {
           CircularProgressIndicator(
               modifier = Modifier.align(Alignment.Center),
               color =MaterialTheme.colors.primary,
           )
       }


    }


    }


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UpcomingMovieItem(
    modifier: Modifier,
    movie: Movie,
    onMovieClicked: (Movie) -> Unit) {

    Box(
        modifier= modifier
            .padding(end = 16.dp)
            .size(120.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .clickable { onMovieClicked.invoke(movie) },


    ) {
        var isImageLoading by remember { mutableStateOf(false) }

        val painter = rememberAsyncImagePainter(
            model = "${Constants.IMAGE_URL}${movie.posterPath}",
        )

        isImageLoading = when (painter.state) {
            is AsyncImagePainter.State.Loading -> true
            else -> false
        }


        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(120.dp),
            painter = painter,
            contentDescription = "Poster Image",
            contentScale = ContentScale.FillBounds,
        )


        if (isImageLoading) {

               CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color =MaterialTheme.colors.primary,
            )
        }


                }

            }





@OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun TrendMovieItem(
    modifier: Modifier,
    movie: Movie,
    onMovieClicked:(Movie)-> Unit
) {
    Box {
        var isImageLoading by remember { mutableStateOf(false) }
        Card(
            modifier
                .width(300.dp)
                .padding(12.dp)
                .clickable { onMovieClicked.invoke(movie!!) },
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp)

        ) {

            if (movie.posterPath != null) {


                val painter = rememberAsyncImagePainter(
                    model = "${Constants.IMAGE_URL}${movie?.posterPath}",
                )

                isImageLoading = when(painter.state) {
                    is AsyncImagePainter.State.Loading -> true
                    else -> false
                }


                    Image(
                        modifier = Modifier
                            .height(200.dp)
                            .width(300.dp)
                            ,
                        painter = painter,
                        contentDescription = "Poster Image",
                        contentScale = ContentScale.FillWidth,
                    )



            }


        }
        if (isImageLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color =MaterialTheme.colors.primary,
            )
        }

        Text(
            text = movie.title,
            style = MaterialTheme.typography.h6,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)

        )
    }
}




@Composable
fun HomeSearchBar(
    modifier: Modifier,
    onSearchClick:()->Unit
) {
    TextField(
        value = "",
        onValueChange = {},
        enabled = false,
        modifier = modifier
            .clickable {
                Log.e(TAG, "HomeSearchBar:hello ",)
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