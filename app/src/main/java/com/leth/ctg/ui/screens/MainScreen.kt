package com.leth.ctg.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.leth.ctg.ui.navigation.AppNavHost
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.ui.views.BottomBar

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    CTGTheme {
        val navController = rememberAnimatedNavController()
        Scaffold(
            bottomBar = { BottomBar(navController = navController) },
        ) { paddingValues ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
