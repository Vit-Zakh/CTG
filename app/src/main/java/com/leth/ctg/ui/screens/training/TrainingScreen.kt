package com.leth.ctg.ui.screens.training

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leth.ctg.ui.views.ExerciseItem
import com.leth.ctg.ui.views.TrainingProgressBar
import com.leth.ctg.ui.views.TransparentButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen(
    trainingId: String?,
    trainingTitle: String?,
    navigation: NavController,
    modifier: Modifier,
    viewModel: TrainingViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.fetchTraining(trainingId ?: "")
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    trainingTitle.toString(),
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
        if (state.value.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                )
            }
        } else {
            Text(
                text = "${state.value.training?.exercises?.size} exercises",
                modifier = Modifier.height(42.dp)
            )
            Log.d("VZ_TAG", "rendering list: ")
            state.value.training?.exercises?.let { exercises ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(exercises) {
                        ExerciseItem(
                            exerciseModel = it,
                            regenerate = { viewModel.regenerateExercise(it) },
                            isTrainingActive = state.value.isActive,
                            completeExercise = { viewModel.completeExercise(it.id) }
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (state.value.isActive) viewModel.completeTraining() else
                        viewModel.startTraining()
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp)
            ) {
                Text(
                    text = if (state.value.isActive) "Complete training" else "Start training",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
            }
            if (!state.value.isActive) {
                TransparentButton(
                    onClick = { viewModel.regenerateTraining() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ButtonDefaults.MinHeight * 1.5f)
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh, contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = "Regenerate training",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
            } else {
                TrainingProgressBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ButtonDefaults.MinHeight * 1.5f)
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                    progress = state.value.completionProgress
                )
            }
        }
    }
}
