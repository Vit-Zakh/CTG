package com.leth.ctg.ui.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.leth.ctg.R
import com.leth.ctg.ui.navigation.TRAININGS

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int,
) {
    object Training : BottomBarScreen(
        route = TRAININGS,
        title = "Trainings",
        icon = R.drawable.ic_exercise,
    )

    object Preferences : BottomBarScreen(
        route = "preferences_screen",
        title = "Preferences",
        icon = R.drawable.ic_trainings_list,
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(BottomBarScreen.Training, BottomBarScreen.Preferences)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val graph = currentDestination?.parent


    NavigationBar(modifier = Modifier) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    NavigationBarItem(
        selected = currentDestination?.let { destination ->
            destination.hierarchy.any { it.route == screen.route }
        } == true,
        onClick = { navController.navigate(screen.route) },
        label = { Text(text = screen.title) },
        icon = {
            Icon(
                painterResource(id = screen.icon), contentDescription = screen.title,
                modifier = Modifier.fillMaxSize(0.35f)
            )
        },
    )
}
