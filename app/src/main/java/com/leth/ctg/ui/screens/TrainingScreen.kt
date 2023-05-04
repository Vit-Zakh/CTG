package com.leth.ctg.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TrainingScreen(trainingType: String?) {
    Text(
        text = "$trainingType",
        modifier = Modifier
    )
}
