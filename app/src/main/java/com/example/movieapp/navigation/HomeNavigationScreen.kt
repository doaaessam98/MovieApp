package com.example.movieapp.navigation

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeNavigationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()

) {
    var isTopBarVisible by remember { mutableStateOf(true) }

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { HomeBottomBarNavigation(modifier, navController)}
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
        ) {
            AppNav(modifier,navController)
        }

    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeBottomBarNavigation(
    modifier: Modifier,
    navController: NavHostController) {
    val navItems = listOf(
        HomeNavigationItem.Home,
        HomeNavigationItem.Category,
        HomeNavigationItem.Favourite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val bottomBarDestination = navItems.any { it.route == currentDestination }
    if (bottomBarDestination) {
        BottomNavigation(modifier.fillMaxWidth(), backgroundColor = Color.White) {
            navItems.forEach { screen ->

                AddItem(screen = screen,
                    currentDestination = currentDestination,
                    navController = navController)



            }
        }
    }else{
        Log.e(ContentValues.TAG, "HomeBottomBarNavigation: ", )

    }


}
@Composable
fun RowScope.AddItem(
    screen: HomeNavigationItem,
    currentDestination: String?,
    navController: NavHostController
) {

    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(id = screen.title)
            )
        },

        label = { Text(text = stringResource(id = screen.title)) },

        alwaysShowLabel = true,
        selected = currentDestination == screen.route,
        selectedContentColor = Color(0xFF407BFF),
        unselectedContentColor = Color.Black,

        onClick = {
            navController.navigate(screen.route) {

                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }

                launchSingleTop = true

                restoreState = true

            }
        }


    )
}

