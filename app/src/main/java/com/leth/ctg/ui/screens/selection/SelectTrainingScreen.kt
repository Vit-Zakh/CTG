package com.leth.ctg.ui.screens.selection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leth.ctg.ui.navigation.getTrainingScreenPath
import com.leth.ctg.ui.views.TrainingItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectTrainingScreen(
    navigation: NavController,
    viewModel: SelectTrainingViewModel = hiltViewModel(),
    modifier: Modifier,
) {

    val state = viewModel.state.collectAsState()

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

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(state.value.list) { index, item ->
                TrainingItem(
                    title = item.title,
                    modifier = Modifier
                        .padding(if (index == 1) PaddingValues(top = 48.dp) else PaddingValues())
                        .aspectRatio(9 / 13f)
                        .clickable { navigation.navigate(getTrainingScreenPath(item.title)) }
                )
            }
        }
    }
}
