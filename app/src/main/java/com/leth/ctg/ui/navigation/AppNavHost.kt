package com.leth.ctg.ui.navigation

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.leth.ctg.ui.screens.preferences.PreferencesScreen
import com.leth.ctg.ui.screens.selection.SelectTrainingScreen
import com.leth.ctg.ui.screens.training.TrainingScreen

private const val TWEEN_FOR_NAVIGATION = 200
private const val KEY_TRAINING_ID = "training_id"

sealed class Screens(val route: String) {
    object Preferences : Screens(route = "preferences_screen")

    object Select : Screens(route = "select_training_screen")
    object Training :
        Screens(route = "training_screen/{${KEY_TRAINING_ID}}")

    fun getTrainingScreenPath(trainingId: String?): String =
        if (trainingId != null) {
            "training_screen/$trainingId"
        } else {
            "training_screen/${""}"
        }
}

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
        startDestination = AUTHENTICATION_ROUTE,
    ) {
        authenticationNavGraph(navController = navController, modifier = modifier)
        composable(route = Screens.Select.route) {
            SelectTrainingScreen(
                navigation = navController,
                modifier = modifier,
            )
        }
        composable(
            route = Screens.Training.route,
            arguments = listOf(
                navArgument(KEY_TRAINING_ID) {
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->
            TrainingScreen(
                trainingId = backStackEntry.arguments?.getString(
                    KEY_TRAINING_ID,
                    ""
                ),
                modifier = modifier,
                navigation = navController,
            )
        }
        composable(route = Screens.Preferences.route) {
            PreferencesScreen(
                navigation = navController,
                modifier = modifier,
            )
        }
    }
}
