package com.leth.ctg.ui.screens.preferences

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
import androidx.compose.material3.DismissDirection.*
import androidx.compose.material3.DismissValue.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.ui.views.CtgTitle
import com.leth.ctg.ui.views.TrainingSetupItemWithSwipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navigation: NavController,
    viewModel: PreferencesViewModel = hiltViewModel(),
    modifier: Modifier,
) {

    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

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
            items(state.value.list) { item ->

                TrainingSetupItemWithSwipe(
                    training = item,
                    onTagClick = viewModel::updateTraining,
                    delayedTrainingUpdate = viewModel::updateTrainingWithDelay,
                    onSwipe = viewModel::deletePreference,
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
