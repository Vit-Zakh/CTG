package com.leth.ctg.ui.screens.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.ui.views.TrainingSetupItem

@Composable
fun PreferencesScreen(
    navigation: NavController,
    viewModel: PreferencesViewModel = hiltViewModel(),
    modifier: Modifier,
) {

    val state = viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(text = "Preferences")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(state.value.list) {
                TrainingSetupItem(
                    training = it,
                    onTagClick = {})
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
