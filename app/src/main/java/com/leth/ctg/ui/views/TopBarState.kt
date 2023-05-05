package com.leth.ctg.ui.views

import androidx.appcompat.view.menu.ActionMenuItem
import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarState(
    val isAppBarVisible: Boolean = false,
    val navigationIcon: ImageVector? = null,
    val navigationIconContentDescription: String? = null,
    val onNavigationIconClick: (() -> Unit)? = null,
    val title: String,
    val actions: List<ActionMenuItem> = emptyList(),
)