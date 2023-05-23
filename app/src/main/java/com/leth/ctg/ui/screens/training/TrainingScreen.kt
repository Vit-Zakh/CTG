package com.leth.ctg.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leth.ctg.ui.screens.selection.SelectTrainingViewModel
import com.leth.ctg.ui.screens.training.TrainingViewModel
import com.leth.ctg.ui.views.ExerciseItem
import com.leth.ctg.ui.views.TrainingSetupItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen(
    trainingType: String?,
    navigation: NavController,
    modifier: Modifier,
    viewModel: TrainingViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()

    viewModel.fetchTraining()

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    trainingType ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigation.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back to trainings list"
                    )
                }
            },
        )



        Text(
//        text = "$trainingType",
            text = "${state.value.training?.exercises?.size} exercises",
            modifier = Modifier.height(42.dp)
        )
        if (state.value.training == null) {
            // render error
        } else {
            state.value.training?.exercises?.let { exercises ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(exercises) {
                        ExerciseItem(
                            exerciseModel = it,
                        )
                    }
                }
            }
        }
    }
}
