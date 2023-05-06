package com.leth.ctg.ui.navigation

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.leth.ctg.ui.screens.PreferencesScreen

const val PREFERENCES_SCREEN = "preferences_screen"

private const val TWEEN_FOR_NAVIGATION = 200

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        enterTransition = { fadeIn(animationSpec = tween(TWEEN_FOR_NAVIGATION)) },
        exitTransition = { ExitTransition.None },
        startDestination = TRAININGS,
    ) {
        trainingsNavGraph(navController, modifier)
        composable(route = PREFERENCES_SCREEN) {
            PreferencesScreen(
                navigation = navController,
                modifier = modifier,
            )
        }
    }
}
