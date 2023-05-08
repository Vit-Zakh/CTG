package com.leth.ctg.ui.views

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leth.ctg.Greeting
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.ui.theme.CTGTheme
import com.leth.ctg.utils.TrainingTag

@Composable
fun TrainingSetupItem(
//    modifier: Modifier,
    training: TrainingSetupModel,
    onTagClick: (TrainingTag) -> Unit,
) {
    Column(
        modifier = Modifier
//            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = training.title,
            modifier = Modifier.wrapContentWidth()
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = Color.Blue),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.Top,
        ) {
            items(TrainingTag.values()) {
                Text(
                    text = it.name,
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(shape = CircleShape, color = Color.Green)
                        .padding(horizontal = 8.dp)
                        .clickable { onTagClick.invoke(it) }
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
//            modifier = Modifier,
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
