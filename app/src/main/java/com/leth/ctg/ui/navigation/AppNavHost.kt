package com.leth.ctg.ui.navigation

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.leth.ctg.ui.navigation.AppDestinations.SELECT_TRAINING_SCREEN
import com.leth.ctg.ui.navigation.AppDestinations.TRAINING_SCREEN
import com.leth.ctg.ui.screens.SelectTrainingScreen
import com.leth.ctg.ui.screens.TrainingScreen
import com.leth.ctg.utils.TrainingTypes

object AppDestinations {
    const val KEY_TRAINING_TYPE = "training_type"

    const val SELECT_TRAINING_SCREEN = "select_training_screen"
    const val TRAINING_SCREEN = "training_screen/{$KEY_TRAINING_TYPE}"

    fun getTrainingScreenPath(trainingType: String?): String =
        if (!trainingType.isNullOrBlank()) {
            "training_screen/$trainingType"
        } else {
            "training_screen/${TrainingTypes.UNKNOWN.title}"
        }
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
        startDestination = SELECT_TRAINING_SCREEN,
    ) {
        composable(SELECT_TRAINING_SCREEN) {
            SelectTrainingScreen(navController)
        }
        composable(
            route = TRAINING_SCREEN,
            arguments = listOf(navArgument(AppDestinations.KEY_TRAINING_TYPE) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            TrainingScreen(
                trainingType = backStackEntry.arguments?.getString(
                    AppDestinations.KEY_TRAINING_TYPE,
                    TrainingTypes.UNKNOWN.title
                )
            )
        }
    }
}
