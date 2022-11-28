package com.example.movieapp.Screens.view.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.movieapp.R
import com.example.movieapp.Screens.uiState.HomeState
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen (modifier: Modifier,viewModel: HomeViewModel= hiltViewModel()){
  val moviesState = viewModel.popularMoviesState.collectAsState().value
  Scaffold(
      topBar = {}
  ) {

    ListOfMovies(modifier,moviesState)
  }

}

@Composable
fun ListOfMovies(modifier: Modifier, moviesState: HomeState) {

    moviesState.let { state->
        when(state){
            is HomeState.Loading->{
                Log.e(TAG, "ListOfMovies: loading", )
            }
            is HomeState.Error->{
                Log.e(TAG, "ListOfMovies: error${state.errorMessage}", )
            }
            is HomeState.Movies->{
                Log.e(TAG, "ListOfMovies: ${state.data}", )
                ShowMovies(modifier,state.data)
            }
            else -> {}
        }

    }
}

@Composable
fun ShowMovies(modifier: Modifier,data: Flow<PagingData<Movie>>) {
    val movies = data.collectAsLazyPagingItems()
    Log.e(TAG, "ShowMovies: ${movies.itemCount}", )
    LazyColumn(){
        items(movies){movie->
            MovieItem(modifier,movie)
        }
    }
    
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(modifier: Modifier,movie: Movie?) {
    Log.e(TAG, "MovieItem: ${movie}", )
    Card(
        modifier
            .fillMaxWidth()
            .padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            GlideImage(
                model = movie?.posterPath,
                contentDescription = "",

            )
            Text(text = movie!!.title,modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}


