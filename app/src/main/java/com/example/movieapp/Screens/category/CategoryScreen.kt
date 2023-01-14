package com.example.movieapp.Screens.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieapp.R

@Composable
 fun CategoryScreen(modifier: Modifier,navController: NavHostController){}




@Composable
fun Categories(modifier: Modifier) {
 Box(modifier.fillMaxWidth()) {
  Text(text = stringResource(id = R.string.category),
   style = MaterialTheme.typography.h5,
   color = Color.Blue,
   modifier=modifier.align(Alignment.CenterStart))
  Text(text = stringResource(id = R.string.see_all),
   style = MaterialTheme.typography.subtitle1,
   color = Color.Blue,
   modifier=modifier.align(Alignment.CenterEnd))
 }

 val categories = listOf("Action")
 val categoryIcon = listOf<Int>(R.drawable.ic_launcher_background)
 LazyRow() {
  itemsIndexed(categories){index,category ->
   categoryItem(
    modifier,
    category,
    categoryIcon[index],
    {})

  }}
}
@Composable
fun categoryItem(
 modifier: Modifier,
 category: String,
 icon:Int,
 onCategoryClicked:(String)->Unit
) {
 Column() {
  Card(
   elevation = 4.dp,
   backgroundColor = Color.Blue,
   shape = RoundedCornerShape(8.dp),
   modifier = modifier
    .size(48.dp)
    .padding(12.dp)
    .clickable { onCategoryClicked.invoke(category) },

   ) {
   Icon(painter = painterResource(icon), contentDescription =category)
  }
  Text(
   text = category,
   style = MaterialTheme.typography.h6,
   color = Color.White, modifier = modifier.padding(8.dp)
  )
 }
}