package com.leth.ctg.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.leth.ctg.ui.screens.signin.SignInScreen
import com.leth.ctg.ui.screens.signup.SignUpScreen

const val AUTHENTICATION_ROUTE = "authentication_route"

sealed class AuthenticationScreen(val route: String) {

    object SignIn : AuthenticationScreen(route = "sign_in_screen")
    object SignUp : AuthenticationScreen(route = "sign_up_screen")
}
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authenticationNavGraph(navController: NavHostController, modifier: Modifier) {
    navigation(
        route = AUTHENTICATION_ROUTE,
        startDestination = AuthenticationScreen.SignIn.route
    ) {
        composable(route = AuthenticationScreen.SignIn.route) {
            SignInScreen(
                navigation = navController,
                modifier = modifier,
            )
        }
        composable(
            route = AuthenticationScreen.SignUp.route) { backStackEntry ->
            SignUpScreen(
                navigation = navController,
                modifier = modifier,
            )
        }
    }
}
