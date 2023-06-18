package com.leth.ctg.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.leth.ctg.R
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.ui.theme.Purple80
import com.leth.ctg.utils.TrainingType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingSetupItem(
    training: TrainingSetupModel,
    onTagClick: (TrainingSetupModel) -> Unit,
    delayedTrainingUpdate: (TrainingSetupModel) -> Unit,
) {

    val selectedTags = remember { mutableStateOf(training.tags) }
    val titleValue = remember { mutableStateOf(training.title) }
    val isLoading = remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(16 / 9f)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.LightGray),
        horizontalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            model = R.drawable.test_image_ctg,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .fillMaxHeight()
                .shimmerEffect(isLoading.value),
            alignment = Alignment.Center,
            onSuccess = { isLoading.value = false },
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Purple80),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = titleValue.value,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                ),
                onValueChange = {
                    titleValue.value = it
                    delayedTrainingUpdate.invoke(training.copy(title = it))
                },
                placeholder = { if (titleValue.value.isEmpty()) Text(text = "Training Title") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .wrapContentWidth(),
                userScrollEnabled = false,
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(TrainingType.values()) {
                    Text(
                        text = it.name.lowercase().replaceFirstChar { it.uppercaseChar() },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                shape = CircleShape, color =
                                if (selectedTags.value.contains(it)) Color.Green else Color.DarkGray
                            )
                            .padding(horizontal = 8.dp)
                            .clickable {
                                selectedTags.value
                                    .toMutableList()
                                    .also { list ->
                                        if (list.contains(it)) list.remove(it) else list.add(it)
                                        selectedTags.value = list
                                    }
                                onTagClick.invoke(training.copy(tags = selectedTags.value))
                            }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = if (training.isEnabled) "Enabled" else "Click to enable")
                Checkbox(
                    checked = training.isEnabled,
                    onCheckedChange = {
                        val updatedValue = !training.isEnabled
                        onTagClick.invoke(training.copy(isEnabled = updatedValue))
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingSetupItemPreview() {
    CTGTheme {
        TrainingSetupItem(
            training = TrainingSetupModel(
                id = "1L",
                title = "Test Title",
                imageUrl = null,
                tags = emptyList(),
                isEnabled = false,
            ),
            {}
        )
        {}
    }
}
