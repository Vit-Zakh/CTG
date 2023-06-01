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
import com.leth.ctg.ui.screens.training.TrainingScreen

//const val KEY_TRAINING_ID = "training_id"

//fun getTrainingScreenPath(trainingId: Long?): String =
//    if (trainingId != null) {
//        "training_screen/$trainingId"
//    } else {
//        "training_screen/${-1L}"
//    }

//sealed class TrainingScreen(val route: String) {
//    object Select : TrainingScreen(route = "select_training_screen")
//    object Training :
//        TrainingScreen(route = "training_screen/{${KEY_TRAINING_ID}}")
//}

//@OptIn(ExperimentalAnimationApi::class)
//fun NavGraphBuilder.trainingsNavGraph(navController: NavHostController, modifier: Modifier) {
//    navigation(
//        route = TRAININGS,
//        startDestination = TrainingScreen.Select.route
//    ) {
//        composable(route = TrainingScreen.Select.route) {
//            SelectTrainingScreen(
//                navigation = navController,
//                modifier = modifier,
//            )
//        }
//        composable(
//            route = TrainingScreen.Training.route,
//            arguments = listOf(
//                navArgument(KEY_TRAINING_ID) {
//                    type = NavType.LongType
//                },
//            )
//        ) { backStackEntry ->
//            TrainingScreen(
//                trainingId = backStackEntry.arguments?.getLong(
//                    KEY_TRAINING_ID,
//                    -1L
//                ),
//                modifier = modifier,
//                navigation = navController,
//            )
//        }
//    }
//}
