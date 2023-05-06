package com.leth.ctg.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.leth.ctg.ui.navigation.getTrainingScreenPath
import com.leth.ctg.utils.TrainingTypes

@Composable
fun SelectTrainingScreen(
    navigation: NavController,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navigation.navigate(getTrainingScreenPath(TrainingTypes.TYPE_ONE.title)) }) {
            Text(text = TrainingTypes.TYPE_ONE.title)
        }
        Button(onClick = { navigation.navigate(getTrainingScreenPath(TrainingTypes.TYPE_TWO.title)) }) {
            Text(text = TrainingTypes.TYPE_TWO.title)
        }
        Button(onClick = { navigation.navigate(getTrainingScreenPath(TrainingTypes.TYPE_THREE.title)) }) {
            Text(text = TrainingTypes.TYPE_THREE.title)
        }
        Button(onClick = { navigation.navigate(getTrainingScreenPath(TrainingTypes.TYPE_CROSSFIT.title)) }) {
            Text(text = TrainingTypes.TYPE_CROSSFIT.title)
        }
    }
}
