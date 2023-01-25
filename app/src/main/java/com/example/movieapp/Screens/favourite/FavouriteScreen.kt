package com.example.movieapp.Screens.favourite

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Search
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movieapp.R
import com.example.movieapp.Screens.home.HomeSearchBar
import com.example.movieapp.Screens.home.HomeSideEffect
import com.example.movieapp.Screens.search.SearchBar
import com.example.movieapp.Screens.search.SearchIntent
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
import com.example.movieapp.navigation.Screen
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
fun FavouriteScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: FavouriteViewModel= hiltViewModel()
 ){


     var isSearch by rememberSaveable { mutableStateOf(false) }
    val state=viewModel.viewState.value
    val favouriteMovies:List<Movie>?= state.FavouriteMovies?.collectAsState(initial = emptyList())?.value?.toSet()?.toList()
     var showDialog by rememberSaveable { mutableStateOf(false) }
    val searchQuery =viewModel.searchQuery.collectAsState(initial = "")
    val sideEffect =viewModel.effect
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val context  = LocalContext.current


    Box{
     ConstraintLayout() {
         val (topBox,ContentBox) = createRefs()
         val topGuideline = createGuidelineFromTop(80.dp)
         Box(
             modifier
                 .height(150.dp)
                 .fillMaxWidth()
                 .background(MaterialTheme.colors.secondary)
                 .constrainAs(topBox) {
                     top.linkTo(parent.top)
                     start.linkTo(parent.start)

                 })
         {
             ConstraintLayout(
                 modifier
                     .fillMaxWidth()
                     .padding(vertical = 16.dp))
             {
                 val (back,fav_name,search) = createRefs()

             IconButton(onClick = {
                       if(isSearch){
                           isSearch=false
                           viewModel.setEvent(FavouriteIntent.FetchFavouriteMovies)

                       }
                 else{
                     viewModel.setEvent(FavouriteIntent.BackToHome)
                 }
             },
                 modifier.constrainAs(back){
                     top.linkTo(parent.top)
                     start.linkTo(parent.start)

                 }
             ) {
                 Icon(imageVector = if(isSearch)Icons.Rounded.Close else Icons.Rounded.ArrowBack,
                         tint = Color.White,
                         contentDescription = stringResource(id = R.string.btn_search_fav)
                     )
                 }
        if(!isSearch){
             IconButton(onClick = {
                    isSearch = true
             },
                 modifier.constrainAs(search){
                     top.linkTo(parent.top)
                     end.linkTo(parent.end)
                 }
                 ) {
                 Icon(imageVector = Icons.Rounded.Search,
                     tint = Color.White,
                     contentDescription = stringResource(id = R.string.btn_search_fav)
                 )
             }

             Text(text = stringResource(id = R.string.favourite) ,
                 style = MaterialTheme.typography.h6,
                 fontSize = 24.sp,
                 fontWeight = FontWeight.Bold,
                 color = Color.White,
                 modifier=modifier.constrainAs(fav_name){
                     top.linkTo(parent.top,4.dp)
                     start.linkTo(back.end,8.dp)

                 }
             )

         }else{
                 SearchBar(modifier.padding(start = 48.dp, bottom = 8.dp, end = 16.dp),
                 searchInput=searchQuery.value?:"",
                 onValueChange = { viewModel.setEvent(FavouriteIntent.SearchInFavourite(it))})

         }
             }
         }
         state.let {
             when{
               it.isLoading!!->{
                  

               }
               else->{
                   if(favouriteMovies!!.isEmpty()){
                       val composition by rememberLottieComposition(
                           LottieCompositionSpec
                               .RawRes(R.raw.favourite)
                       )
                       Column(
                             horizontalAlignment = Alignment.CenterHorizontally ,
                           verticalArrangement = Arrangement.Center,
                          modifier= modifier
                              .fillMaxWidth().fillMaxHeight()
                              .padding(start = 16.dp, bottom = 80.dp)
                              .constrainAs(ContentBox) {
                                  top.linkTo(topGuideline, 16.dp)
                                  start.linkTo(parent.start)
                              }) {
                           LottieAnimation(
                               composition= composition,
                               iterations = LottieConstants.IterateForever
                           )
                           }
                     }else{
                         LazyColumn(
                             modifier
                                 .fillMaxWidth()
                                 .padding(start = 16.dp, bottom = 80.dp)
                                 .constrainAs(ContentBox) {
                                     top.linkTo(topGuideline, 16.dp)
                                     start.linkTo(parent.start)
                                 }){
                                       items(favouriteMovies.toList()){ movie->
                                         FavouriteMovieItem(modifier, movie,
                                         onMovieClick = { viewModel.setEvent(FavouriteIntent.OpenDetails(movie))},
                                         onRemoveFromFavClick = {
                                             showDialog=true
                                             viewModel.removeMovieId =it
                                         }
                                     )
                                               if(showDialog){                                                                              
                                                   ConfirmDialog(modifier = modifier,
                                                     onConfirmClick = {
                                                         viewModel.setEvent(FavouriteIntent.RemoveMovieFromFavourite)
                                                         showDialog=false

                                                     }, onDismissClick = {
                                                     showDialog=false
                                                     })
                                                                                                                                            
                                             }                                                                                              
                                 }
                             }
                     }
                 }
             }
         }

     }}
    LaunchedEffect(Constants.SIDE_EFFECTS_KEY) {

        sideEffect.onEach { effect ->
            when (effect) {
                is FavouriteSideEffect.Navigation.OpenMovieDetails -> {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = Constants.MOVIE_NAVIGATION_KEY,
                        value =effect.movie
                    )
                    navController.navigate(Screen.Details.route)
                }
                is FavouriteSideEffect.ShowLoadDataError-> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Long
                    )
                }
                is FavouriteSideEffect.Navigation.BackToHome->{
                     navController.popBackStack()
                }
                is FavouriteSideEffect.ShowToast->{
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.collect()
    }

}


@Composable
fun ShowLoadingScreen(modifier: Modifier) {

    Column(modifier = modifier
        .padding(top = 80.dp)
        .verticalScroll(rememberScrollState())) {
        LoadingItem(modifier)
        LoadingItem(modifier)
        LoadingItem(modifier)
        LoadingItem(modifier)
    }
}

@Composable
fun LoadingItem(modifier: Modifier) {

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp)

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

               Box(modifier= modifier
                   .height(32.dp)
                   .fillMaxWidth()
                   .padding(horizontal = 64.dp)
                   .background(Color.LightGray.copy(alpha = alpha))
                   .constrainAs(name) {
                       top.linkTo(parent.top, 8.dp)
                       end.linkTo(parent.end)
                       start.linkTo(startGuideline)

                   })

                   Box(modifier= modifier
                       .height(32.dp)
                       .fillMaxWidth()
                       .padding(horizontal = 64.dp)
                       .background(Color.LightGray.copy(alpha = alpha))
                       .constrainAs(releaseYear) {
                           top.linkTo(name.bottom, 16.dp)
                           end.linkTo(parent.end)
                           start.linkTo(startGuideline)
                       })



                   Box(modifier = modifier
                       .height(32.dp)
                       .fillMaxWidth()
                       .padding(horizontal = 64.dp)
                       .background(Color.LightGray.copy(alpha = alpha))
                       .padding(bottom = 16.dp)
                       .constrainAs(rate) {
                           top.linkTo(releaseYear.bottom, 16.dp)
                           start.linkTo(parent.start)
                           end.linkTo(parent.end, 8.dp)
                       })
            }
               }

        Box(modifier  = modifier
            .background(Color.LightGray.copy(alpha = alpha))
            .align(Alignment.TopStart)
            .padding(bottom = 16.dp)
            .height(140.dp)
            .width(140.dp)

        )

    }
}

@Composable
fun FavouriteMovieItem(
    modifier: Modifier,
    movie: Movie,
    onMovieClick: () -> Unit,
    onRemoveFromFavClick:(Int)->Unit) {
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

             Icon(painter = painterResource(id = R.drawable.heart_remove_24px),
                 tint = Color.Red,
                 contentDescription = stringResource(id = R.string.delete_fromFav_icon),
                 modifier = modifier
                     .align(Alignment.BottomEnd)
                     .padding(end = 8.dp)
                     .clickable {
                         onRemoveFromFavClick.invoke(movie.id)
                     })


                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                       .data("${Constants.IMAGE_URL}${movie?.posterPath}")
                        .crossfade(true)
                        .build(),
                    //placeholder = painterResource(R.drawable.images),
                    contentDescription = stringResource(R.string.details_image_fav),
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun ConfirmDialog(
    modifier: Modifier,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {

       AlertDialog(
            onDismissRequest = { onDismissClick.invoke()},

            title = {
                Text(text = stringResource(id = R.string.confirm_dilog_title))
            },
            text = {
                Text(stringResource(id = R.string.delete_dialog_message))
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmClick.invoke()
                    }) {
                    Text(stringResource(id = R.string.delete))
                }
                },
            dismissButton = {

                Button(
                    onClick = {
                        onDismissClick.invoke()
                    }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }

        )
}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun Preview(){
    FavouriteScreen(Modifier, rememberNavController())
}