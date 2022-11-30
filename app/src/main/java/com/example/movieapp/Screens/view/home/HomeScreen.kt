package com.example.movieapp.Screens.view.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieapp.Screens.intent.HomeIntent
import com.example.movieapp.Screens.sideEfect.HomeSideEffect
import com.example.movieapp.Screens.uiState.HomeState
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Movie
import com.example.movieapp.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen (modifier: Modifier,viewModel: HomeViewModel= hiltViewModel(),navController:NavHostController){
  val moviesState = viewModel.viewState.value
    val sideEffect =viewModel.effect
    val scaffoldState: ScaffoldState = rememberScaffoldState()


  Scaffold(
      scaffoldState = scaffoldState,
      topBar = {}
  ) {

     ListOfMovies(modifier,moviesState){movie->
         viewModel.setEvent(HomeIntent.MovieSelected(movie))
     }

  }
    LaunchedEffect(Constants.SIDE_EFFECTS_KEY) {
        sideEffect.onEach { effect ->
               when (effect) {
                   is HomeSideEffect.Navigation.OpenMovieDetails -> {
                       navController.navigate(Screen.MovieDetailsScreen.route)

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
fun ListOfMovies(modifier: Modifier, moviesState: HomeState, onMovieClicked: (Movie?) -> Unit) {

    moviesState.let { state->
        when{
            state.isLoading!!->{
                Log.e(TAG, "ListOfMovies: loading", )
            }

            else->{

                ShowMovies(modifier,state.movies!!,onMovieClicked)
            }

        }

    }
}

@Composable
fun ShowMovies(modifier: Modifier,data: Flow<PagingData<Movie>>,onMovieClicked: (Movie?) -> Unit) {
    val movies = data.collectAsLazyPagingItems()
    LazyColumn(){
        items(movies){movie->
            MovieItem(modifier,movie,onMovieClicked)
        }
    }
    
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(modifier: Modifier, movie: Movie?, onMovieClicked: (Movie?) -> Unit) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(16.dp).clickable{onMovieClicked.invoke(movie)}) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Log.e(TAG, "MovieItem:${Constants.IMAGE_URL}${movie?.posterPath} " )
            GlideImage(
                model = "${Constants.IMAGE_URL}${movie?.posterPath}",
                contentDescription = "",modifier.requiredSize(200.dp)

            )
            Text(text = movie!!.title,modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}


