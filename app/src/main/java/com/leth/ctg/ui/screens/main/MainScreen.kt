package com.leth.ctg.ui.screens.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.ui.navigation.AUTHENTICATION_ROUTE
import com.leth.ctg.ui.navigation.AppNavHost
import com.leth.ctg.ui.navigation.Screens
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.ui.views.BottomBar

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    CTGTheme {
        val navController = rememberAnimatedNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        var showBottomBar by rememberSaveable { mutableStateOf(true) }

        val context = LocalContext.current

        LaunchedEffect(viewModel, context) {
            viewModel.authResults.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        navController.navigate(Screens.Select.route) {
                            popUpTo(AUTHENTICATION_ROUTE) {
                                inclusive = true
                            }
                        }
                    }
                    else -> {}
                }
            }
        }

        showBottomBar = when (navBackStackEntry?.destination?.route) {
            "select_training_screen" -> true
            "preferences_screen" -> true
            else -> false
        }

        Scaffold(
            bottomBar = { if (showBottomBar) BottomBar(navController = navController) },
        ) { paddingValues ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
