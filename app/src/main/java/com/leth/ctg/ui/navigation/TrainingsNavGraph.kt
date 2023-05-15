package com.leth.ctg.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.leth.ctg.ui.screens.selection.SelectTrainingScreen
import com.leth.ctg.ui.screens.TrainingScreen
import com.leth.ctg.utils.TrainingTypes

const val TRAININGS = "trainings_graph"
const val KEY_TRAINING_TYPE = "training_type"

fun getTrainingScreenPath(trainingType: String?): String =
    if (!trainingType.isNullOrBlank()) {
        "training_screen/$trainingType"
    } else {
        "training_screen/${TrainingTypes.UNKNOWN.title}"
    }

sealed class TrainingScreen(val route: String) {
    object Select : TrainingScreen(route = "select_training_screen")
    object Training :
        TrainingScreen(route = "training_screen/{${KEY_TRAINING_TYPE}}")
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.trainingsNavGraph(navController: NavHostController, modifier: Modifier) {
    navigation(
        route = TRAININGS,
        startDestination = TrainingScreen.Select.route
    ) {
        composable(route = TrainingScreen.Select.route) {
            SelectTrainingScreen(
                navigation = navController,
                modifier = modifier,
            )
        }
        composable(
            route = TrainingScreen.Training.route,
            arguments = listOf(navArgument(KEY_TRAINING_TYPE) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            TrainingScreen(
                trainingType = backStackEntry.arguments?.getString(
                    KEY_TRAINING_TYPE,
                    TrainingTypes.UNKNOWN.title
                ),
                modifier = modifier,
            )
        }
    }
}
