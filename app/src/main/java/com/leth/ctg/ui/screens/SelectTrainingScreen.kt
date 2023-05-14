package com.leth.ctg.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leth.ctg.ui.navigation.getTrainingScreenPath
import com.leth.ctg.ui.views.TrainingItem
import com.leth.ctg.utils.TrainingTypes

@OptIn(ExperimentalFoundationApi::class)
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
        Text(
            text = "Select your \n\n                training",
            textAlign = TextAlign.Justify,
            fontSize = 42.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        val temporaryItems = listOf(
            TrainingTypes.TYPE_ONE.title,
            TrainingTypes.TYPE_TWO.title,
            TrainingTypes.TYPE_THREE.title,
            TrainingTypes.TYPE_CROSSFIT.title,
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(temporaryItems) { index, item ->
                TrainingItem(
                    title = item,
                    modifier = Modifier
                        .padding(if (index == 1) PaddingValues(top = 48.dp) else PaddingValues())
                        .aspectRatio(9 / 13f)
                        .clickable { navigation.navigate(getTrainingScreenPath(item)) }
                )
            }
        }
    }
}
