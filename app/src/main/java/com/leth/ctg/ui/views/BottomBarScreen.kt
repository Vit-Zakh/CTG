package com.leth.ctg.ui.views

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.leth.ctg.ui.navigation.TRAININGS

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Training : BottomBarScreen(
        route = TRAININGS,
        title = "Trainings",
        icon = Icons.Default.Face,
    )

    object Preferences : BottomBarScreen(
        route = "preferences_screen",
        title = "Preferences",
        icon = Icons.Default.Star,
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(BottomBarScreen.Training, BottomBarScreen.Preferences)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val graph = currentDestination?.parent


    NavigationBar() {
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
        icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
    )
}
