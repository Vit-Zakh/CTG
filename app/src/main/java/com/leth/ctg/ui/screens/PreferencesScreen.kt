package com.leth.ctg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.ui.views.TrainingSetupItem

@Composable
fun PreferencesScreen(
    navigation: NavController,
    modifier: Modifier,
) {
    val preferencesList = listOf(
        TrainingSetupModel(
            id = "test_id_1",
            title = "Test Title 1",
            imageUrl = null,
            tags = emptyList()
        ),
        TrainingSetupModel(
            id = "test_id_2",
            title = "Test Title 2",
            imageUrl = null,
            tags = emptyList()
        ),
        TrainingSetupModel(
            id = "test_id_3",
            title = "Test Title 3",
            imageUrl = null,
            tags = emptyList()
        ),
        TrainingSetupModel(
            id = "test_id_4",
            title = "Test Title 4",
            imageUrl = null,
            tags = emptyList()
        )
    )
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
            items(preferencesList) {
                TrainingSetupItem(
//                    modifier = modifier.height(150.dp),
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
