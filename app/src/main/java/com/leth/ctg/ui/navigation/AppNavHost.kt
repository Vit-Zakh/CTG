package com.leth.ctg.ui.navigation

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.leth.ctg.ui.navigation.AppDestinations.SELECT_TRAINING_SCREEN
import com.leth.ctg.ui.navigation.AppDestinations.TRAINING_SCREEN
import com.leth.ctg.ui.screens.SelectTrainingScreen
import com.leth.ctg.ui.screens.TrainingScreen

object AppDestinations {
    const val SELECT_TRAINING_SCREEN = "select_training_screen"
    const val TRAINING_SCREEN = "training_screen"
}

private const val TWEEN_FOR_NAVIGATION = 200

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    AnimatedNavHost(
        navController = navController,
        enterTransition = { fadeIn(animationSpec = tween(TWEEN_FOR_NAVIGATION)) },
        exitTransition = { ExitTransition.None },
        startDestination = SELECT_TRAINING_SCREEN
    ) {
        composable(SELECT_TRAINING_SCREEN) {
            SelectTrainingScreen()
        }
        composable(TRAINING_SCREEN) {
            TrainingScreen()
        }
    }
}
