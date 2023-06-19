package com.leth.ctg.ui.screens.preferences

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.ui.navigation.AUTHENTICATION_ROUTE
import com.leth.ctg.ui.navigation.Screens
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.ui.views.CtgTitle
import com.leth.ctg.ui.views.TrainingSetupItem

@Composable
fun PreferencesScreen(
    navigation: NavController,
    viewModel: PreferencesViewModel = hiltViewModel(),
    modifier: Modifier,
) {

    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

//    LaunchedEffect(context) {
//        Log.d("VZ_TAG", "pref context changed")
//    }
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                Log.d("VZ_TAG", "On stop called")
//                viewModel.fetchTrainings()
//                viewModel.savePreferences()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CtgTitle(
            firstLine = "Customize your", secondLine = "Preferences",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(state.value.list) {
                TrainingSetupItem(
                    training = it,
                    onTagClick = viewModel::updateTraining,
                    delayedTrainingUpdate = viewModel::updateTrainingWithDelay
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Button(
                onClick = { viewModel.addNewTraining() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp)
            ) {
                Text(
                    text = "New Training",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreferencesScreenPreview() {
    CTGTheme {
        PreferencesScreen(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            navigation = rememberNavController()
        )
    }
}
