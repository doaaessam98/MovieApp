package com.example.movieapp.base.common
//
//import androidx.compose.animation.animateColor
//import androidx.compose.animation.core.Spring
//import androidx.compose.animation.core.animateDp
//import androidx.compose.animation.core.spring
//import androidx.compose.animation.core.updateTransition
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.TabPosition
//import androidx.compose.material.TabRow
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountBox
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import com.example.movieapp.R
//import com.example.movieapp.ui.theme.Purple700
//
//@Composable
//private fun HomeCategoryTabs(
//    backgroundColor: Color,
//    tabPage: TabPage,
//    onTabSelected: (tabPage: TabPage) -> Unit
//) {
//    TabRow(
//        selectedTabIndex = tabPage.ordinal,
//        backgroundColor = backgroundColor,
//        indicator = { tabPositions ->
//            HomeTabIndicator(tabPositions, tabPage)
//        }
//    ) {
//
//        CategoryTab(
//            icon = Icons.Default.AccountBox,
//
//            title = stringResource(R.string.action)
//        ) { onTabSelected(TabPage.Action) }
//        CategoryTab(
//            icon = Icons.Default.AccountBox,
//            title = stringResource(R.string.action)
//        ) { onTabSelected(TabPage.Action) }
//        CategoryTab(
//            icon = Icons.Default.AccountBox,
//            title = stringResource(R.string.action)
//        ) { onTabSelected(TabPage.Action) }
//        CategoryTab(
//            icon = Icons.Default.AccountBox,
//            title = stringResource(R.string.action)
//        ) { onTabSelected(TabPage.Action) }
//
//    }
//}
//
//@Composable
//fun CategoryTab(
//    icon: ImageVector,
//    title: String,
//    onClick: () -> Unit) {
//
//}
//
//
//@Composable
//private fun HomeTabIndicator(
//    tabPositions: List<TabPosition>,
//    tabPage: TabPage
//) {
//    val transition = updateTransition(
//        tabPage,
//        label = "Tab indicator"
//    )
//    val indicatorLeft by transition.animateDp(
//        transitionSpec = {
//            if (TabPage.Home isTransitioningTo TabPage.Work) {
//                // Indicator moves to the right.
//                // Low stiffness spring for the left edge so it moves slower than the right edge.
//                spring(stiffness = Spring.StiffnessVeryLow)
//            } else {
//                // Indicator moves to the left.
//                // Medium stiffness spring for the left edge so it moves faster than the right edge.
//                spring(stiffness = Spring.StiffnessMedium)
//            }
//        },
//        label = "Indicator left"
//    ) { page ->
//        tabPositions[page.ordinal].left
//    }
//    val indicatorRight by transition.animateDp(
//        transitionSpec = {
//            if (TabPage.Home isTransitioningTo TabPage.Work) {
//                // Indicator moves to the right
//                // Medium stiffness spring for the right edge so it moves faster than the left edge.
//                spring(stiffness = Spring.StiffnessMedium)
//            } else {
//                // Indicator moves to the left.
//                // Low stiffness spring for the right edge so it moves slower than the left edge.
//                spring(stiffness = Spring.StiffnessVeryLow)
//            }
//        },
//        label = "Indicator right"
//    ) { page ->
//        tabPositions[page.ordinal].right
//    }
//    val color by transition.animateColor(
//        label = "Border color"
//    ) { page ->
//        if (page == TabPage.Home) Purple700 else Green800
//    }
//    Box(
//        Modifier
//            .fillMaxSize()
//            .wrapContentSize(align = Alignment.BottomStart)
//            .offset(x = indicatorLeft)
//            .width(indicatorRight - indicatorLeft)
//            .padding(4.dp)
//            .fillMaxSize()
//            .border(
//                BorderStroke(2.dp, color),
//                RoundedCornerShape(4.dp)
//            )
//    )
//}
//
//private enum class TabPage {
//    Idel,
//    Action,
//    Romantic,
//    Animy,
//    Movies,
//    Movies2
//}
