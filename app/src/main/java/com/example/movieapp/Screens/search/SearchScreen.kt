package com.example.movieapp.Screens.search

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Warning
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
import com.example.movieapp.navigation.Screen
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SearchViewModel= hiltViewModel()
)
    {
        var isSearch by rememberSaveable { mutableStateOf(false) }

       val  searchMoviesResult = viewModel.viewState.value.MoviesResult?.collectAsLazyPagingItems()
        val state=viewModel.viewState.value
        val searchQuery =viewModel.searchBar.collectAsState(initial = "")
        Log.e(TAG, "SearchScreen  searchQuery: $searchQuery", )
        val sideEffect =viewModel.effect
        val scaffoldState: ScaffoldState = rememberScaffoldState()

        Box(){
            ConstraintLayout {
                val (topBox,ContentBox,back, searchIcon) = createRefs()
                val topGuideline = createGuidelineFromTop(60.dp)
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
                    IconButton(onClick = {
                        viewModel.setEvent(SearchIntent.BackToHome)

                    },
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
                            searchInput =searchQuery.value?:"",
                            onValueChange = {
                                Log.e(TAG, "SearchScreen value: $it", )
                                viewModel.setEvent(SearchIntent.FetchMoviesForSearch(it))
                            })
                        }

                if(searchMoviesResult!=null){
                    Log.e(TAG, "SearchScreen: not null ", )
                    LazyColumn(
                        modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 80.dp)
                            .constrainAs(ContentBox) {
                                top.linkTo(topGuideline, 16.dp)
                                start.linkTo(parent.start)
                            }) {
                        items(searchMoviesResult) { movie ->
                            CardMovieItem(
                                modifier = modifier,
                                movie = movie!!,
                                onMovieClick = {
                                    viewModel.setEvent(SearchIntent.MovieSelected(movie))
                                })

                        }
                        //HandelPaginatingLoadingAndError(searchMoviesResult)
                        val loadState = searchMoviesResult.loadState.source
                        item {
                            if (loadState?.refresh == LoadState.Loading) {
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp),
                                        text = "Refresh Loading"
                                    )

                                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                                }
                            }

                            if (loadState?.append == LoadState.Loading) {
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxSize()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                                }
                            }

                            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                                val isPaginatingError = (loadState.append is LoadState.Error) || searchMoviesResult.itemCount > 1
                                val error = if (loadState.append is LoadState.Error)
                                    (loadState.append as LoadState.Error).error
                                else
                                    (loadState.refresh as LoadState.Error).error

                                val modifier = if (isPaginatingError) {
                                    Modifier.padding(8.dp)
                                } else {
                                    Modifier.fillMaxSize()
                                }
                                Column(
                                    modifier = modifier.fillParentMaxSize(),
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
                                        text = error.message ?: error.toString(),
                                        textAlign = TextAlign.Center,
                                    )

                                    Button(
                                        onClick = {
                                            searchMoviesResult.refresh()
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
                }else{
                    Log.e(TAG, "SearchScreen: ", )
                }
                }


            }



//
//        LaunchedEffect(Constants.SIDE_EFFECTS_KEY) {
//            sideEffect.onEach { effect ->
//                when (effect) {
//                    is SearchSideEffect.Navigation.OpenMovieDetails -> {
//                        navController.currentBackStackEntry?.savedStateHandle?.set(
//                            key = Constants.MOVIE_NAVIGATION_KEY,
//                            value =effect.movie
//                        )
//                        navController.navigate(Screen.Details.route){
//
//                        }
//                    }
//                    is SearchSideEffect.ShowLoadDataError->{
//                        scaffoldState.snackbarHostState.showSnackbar(
//                            message = effect.message,
//                            duration = SnackbarDuration.Long
//                        )
//                    }
//                    is SearchSideEffect.Navigation.BackToHome->{
//                        navController.navigate(Screen.Search.route)
//                    }
//
//
//                }
//            }.collect()
//        }







        }





@Composable
fun CardMovieItem(
    modifier: Modifier,movie: Movie,
    onMovieClick: () -> Unit) {
    var rating: Float? by remember { mutableStateOf(movie.voteAverage.toFloat()) }

    Box(
        modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable {
                onMovieClick.invoke()
            }
    ){
        Card(
            elevation = 8.dp,
            shape =RoundedCornerShape(16.dp),
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(start = 32.dp, top = 16.dp, end = 16.dp, bottom = 4.dp))
        {
            ConstraintLayout(modifier.padding( 8.dp)) {
                val (name,releaseYear,rate) = createRefs()
                val startGuideline = createGuidelineFromStart(110.dp)

                Text(text = movie.title,
                    style = MaterialTheme.typography.h6,
                    fontSize = 16.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier= modifier
                        .constrainAs(name) {
                            top.linkTo(parent.top,8.dp)
                            end.linkTo(parent.end,8.dp)
                            start.linkTo(startGuideline,8.dp)

                        }
                )

                Text(
                    text = "${stringResource(id = R.string.released_in)}${movie.releaseDate}",
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier=modifier.constrainAs(releaseYear) {
                        top.linkTo(name.bottom,16.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(parent.end,4.dp)
                    }

                )



                (rating?.div(2)).let {
                    RatingBar(
                        value = rating!!,
                        config = RatingBarConfig()
                            .style(RatingBarStyle.HighLighted),
                        onValueChange = {},
                        onRatingChanged = {},
                        modifier = modifier
                            .padding(bottom = 16.dp)
                            .constrainAs(rate) {
                                top.linkTo(releaseYear.bottom, 16.dp)
                                start.linkTo(name.start)
                                end.linkTo(parent.end, 8.dp)
                            }
                    ) }



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
            modifier  = modifier
                .align(Alignment.TopStart)
                .padding(bottom = 16.dp)
                .height(140.dp)
                .width(140.dp),
            onError = {}
        )

    }
}
@Composable
fun SearchBar(
    modifier: Modifier,
     searchInput: String?,
    onValueChange:(String)->Unit

) {
    TextField(
        value = searchInput!!,
        onValueChange = {
            Log.e(TAG, "SearchBar: $it", )
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