package com.example.movieapp.Screens.category

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.movieapp.R
import com.example.movieapp.Screens.favourite.*
import com.example.movieapp.Screens.home.HomeSideEffect
import com.example.movieapp.Screens.movieDetails.DetailsIntent
import com.example.movieapp.Screens.search.CardMovieItem
import com.example.movieapp.Screens.search.SearchBar
import com.example.movieapp.Utils.Constants
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import com.example.movieapp.navigation.Screen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@SuppressLint("UnrememberedMutableState")
@Composable
   fun CategoryScreen(
   modifier: Modifier,
   navController: NavHostController,
   viewModel:CategoryViewModel= hiltViewModel()
  ) {


      var isSearch by rememberSaveable { mutableStateOf(false) }
      val state = viewModel.viewState.value
      val categoryMovies = state.categoryMovies?.collectAsLazyPagingItems()
      val searchQuery = viewModel.searchQuery.collectAsState(initial = "")
      val categories = viewModel.genres.collectAsState().value
      val sideEffect = viewModel.effect
      val scaffoldState: ScaffoldState = rememberScaffoldState()

      Box {
          ConstraintLayout {
              val (topBox, ContentBox) = createRefs()
              Box(
                  modifier
                      .height(150.dp)
                      .fillMaxWidth()
                      .background(MaterialTheme.colors.secondary)
                      .constrainAs(topBox) {
                          top.linkTo(parent.top)
                          start.linkTo(parent.start)
                      }
              ) {
                  ConstraintLayout(
                      modifier
                          .fillMaxWidth()
                          .padding(vertical = 16.dp))
                  {
                      val (back, categoryName, search, categoriesList) = createRefs()
                      if(isSearch) {
                          IconButton(onClick = {
                              isSearch = false
                              viewModel.setEvent(CategoryIntent.FetchMovies(viewModel.selectedCategory!!.id))
                                               },
                              modifier.constrainAs(back) {
                                  top.linkTo(parent.top)
                                  start.linkTo(parent.start)
                              }) {
                              Icon(
                                  imageVector = Icons.Rounded.Close,
                                  tint = Color.White,
                                  contentDescription = stringResource(id = R.string.btn_search_category)
                              )
                          }
                      }
                      if(!isSearch) {
                          IconButton(onClick = {
                              isSearch = true
                          },
                              modifier.constrainAs(search) {
                                  top.linkTo(parent.top)
                                  end.linkTo(parent.end)
                              }
                          ) {
                              Icon(
                                  imageVector = Icons.Rounded.Search,
                                  tint = Color.White,
                                  contentDescription = stringResource(id = R.string.btn_search_category)
                              )
                          }

                          Text(text = stringResource(id = R.string.category),
                              style = MaterialTheme.typography.h6,
                              fontSize = 24.sp,
                              fontWeight = FontWeight.Bold,
                              color = Color.White,
                              modifier = modifier.constrainAs(categoryName) {
                                  top.linkTo(parent.top, 4.dp)
                                  start.linkTo(parent.start, 16.dp)

                              }
                          )

                      } else {
                          SearchBar(modifier.padding(start = 48.dp, bottom = 8.dp, end = 16.dp),
                              searchInput = searchQuery.value ?: "",
                              onValueChange = {
                                  viewModel.setEvent(CategoryIntent.SearchInCategory(query = it))
                              }
                          )

                      }


                  }
                  if(categories.isNotEmpty()){

                  var selected by remember { mutableStateOf(categories[0].id) }
                  LazyRow(
                      modifier
                          .align(Alignment.BottomStart)
                          .padding(start = 8.dp)) {
                      itemsIndexed(categories) { index,category ->
                          categoryItem(
                              modifier,
                              category,
                              selected = selected,
                          ) {
                               selected = it.id
                              viewModel.setEvent(CategoryIntent.FetchMovies(it.id))
                              viewModel.selectedCategory=it
                              viewModel.setEvent(CategoryIntent.SearchInCategory(category = it))

                          }

                      }
                  }
              }}
                 if(categoryMovies!=null){
                  LazyColumn(
                      modifier
                          .fillMaxWidth()
                          .padding(start = 8.dp, bottom = 120.dp)
                          .constrainAs(ContentBox) {
                              top.linkTo(topBox.bottom, 16.dp)
                              start.linkTo(parent.start)
                          }) {
                            items(categoryMovies) { movie ->
                                  CardMovieItem(
                                  modifier, movie!!,
                                  onMovieClick = { viewModel.setEvent(CategoryIntent.OpenDetails(movie)) },
                                 )
                            }


                         }
              }
      }
     }

      LaunchedEffect(Constants.SIDE_EFFECTS_KEY) {
          viewModel.setEvent(CategoryIntent.FetchMovies(28))
          sideEffect.onEach { effect ->
              when (effect) {
                  is CategorySideEffect.Navigation.OpenMovieDetails -> {
                      navController.currentBackStackEntry?.savedStateHandle?.set(
                          key = Constants.MOVIE_NAVIGATION_KEY,
                          value =effect.movie
                      )
                      navController.navigate(Screen.Details.route){

                      }
                  }
                  is CategorySideEffect.ShowLoadDataError->{
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
  fun categoryItem(
     modifier: Modifier,
      genre: Genre,
      selected:Int,
      onCategoryClicked:(Genre)->Unit
  ){
      val isSelected = selected == genre.id
    Card(
     elevation = 4.dp,
       backgroundColor = if(isSelected ) Color(0xFFFFC6D3) else MaterialTheme.colors.secondary,
     shape = RoundedCornerShape(16.dp),
     modifier = modifier
         .padding(8.dp)
         .border(2.dp, color = Color(0xFFFFC6D3), shape = RoundedCornerShape(16.dp))
         .clickable {
             onCategoryClicked.invoke(genre)

         },

     ) {
        Text(
            text  = genre.name,
            style = MaterialTheme.typography.h6,
            color =if(isSelected ) MaterialTheme.colors.secondary else Color.White,
            modifier = modifier.padding(8.dp)
        )
    }


  }

