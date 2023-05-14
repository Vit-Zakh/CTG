package com.leth.ctg.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.leth.ctg.R
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.utils.TrainingTag

@Composable
fun TrainingSetupItem(
//    modifier: Modifier,
    training: TrainingSetupModel,
    onTagClick: (TrainingTag) -> Unit,
) {

    val isChecked = remember { mutableStateOf(false) }
    val selectedTags = remember { mutableStateOf(training.tags) }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
        ) {
            Icon(
                imageVector = Icons.Default.Close, contentDescription = "", modifier = Modifier
                    .padding(8.dp)
                    .wrapContentWidth()
                    .align(Alignment.TopEnd)
            )
            AsyncImage(
                model = R.drawable.test_image_ctg,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Text(
                text = training.title,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.BottomStart)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Blue),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .wrapContentWidth()
                    .height(56.dp),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalArrangement = Arrangement.Top,
            ) {
                items(TrainingTag.values()) {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .wrapContentHeight()
                            .background(shape = CircleShape, color =
                            if (selectedTags.value.contains(it))Color.Green else Color.DarkGray
                            )
                            .padding(horizontal = 8.dp)
                            .clickable {
                                onTagClick.invoke(it)
                                selectedTags.value
                                    .toMutableList()
                                    .also { list ->
                                        if (list.contains(it)) list.remove(it) else list.add(it)
                                        selectedTags.value = list
                                    }
                            }
                    )
                }
            }
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = !isChecked.value },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingSetupItemPreview() {
    CTGTheme {
        TrainingSetupItem(
            training = TrainingSetupModel(
                id = "test_id",
                title = "Test Title",
                imageUrl = null,
                tags = emptyList()
            )
        )
        {}
    }
}
