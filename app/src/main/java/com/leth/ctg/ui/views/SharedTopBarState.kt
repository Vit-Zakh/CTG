package com.leth.ctg.ui.views

import androidx.appcompat.view.menu.ActionMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

class SharedTopBarState(val navController: NavController) {

    private val currentScreenRoute: String?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination?.route



//    val isVisible: Boolean
//        @Composable get() = currentScreen?.isAppBarVisible == true
//
//    val navigationIcon: ImageVector?
//        @Composable get() = currentScreen?.navigationIcon
//
//    val navigationIconContentDescription: String?
//        @Composable get() = currentScreen?.navigationIconContentDescription
//
//    val onNavigationIconClick: (() -> Unit)?
//        @Composable get() = currentScreen?.onNavigationIconClick
//
//    val title: String
//        @Composable get() = currentScreen?.title.orEmpty()
//
//    val actions: List<ActionMenuItem>
//        @Composable get() = currentScreen?.actions.orEmpty()

}

@Composable
fun rememberAppBarState(
    navController: NavController,
) = remember { SharedTopBarState(navController) }
