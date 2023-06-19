package com.leth.ctg.ui.screens.selection

import android.util.Log
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leth.ctg.ui.navigation.Screens.Preferences.getTrainingScreenPath
import com.leth.ctg.ui.views.CtgTitle
import com.leth.ctg.ui.views.TrainingItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectTrainingScreen(
    navigation: NavController,
    viewModel: SelectTrainingViewModel = hiltViewModel(),
    modifier: Modifier,
) {

    val state = viewModel.state

    val context = LocalContext.current
    LaunchedEffect(context) {
        Log.d("VZ_TAG", "context changed")
        viewModel.fetchDataForLoggedInUser()
        viewModel.subscribeToTrainings()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CtgTitle(
            firstLine = "Select your", secondLine = "Today's Training",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(state.list) { index, item ->
                TrainingItem(
                    title = item.title,
                    modifier = Modifier
                        .padding(if (index == 1) PaddingValues(top = 48.dp) else PaddingValues())
                        .aspectRatio(9 / 13f)
                        .clickable { navigation.navigate(getTrainingScreenPath(item.id, item.title)) }
                )
            }
        }
    }
}
