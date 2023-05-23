package com.leth.ctg.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.leth.ctg.R
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseItem(
    exerciseModel: ExerciseModel,
    regenerate: () -> Unit,
) {

    val isChecked = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(16 / 5f)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.LightGray),
        horizontalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            model = R.drawable.chest_press,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .fillMaxHeight(),
            alignment = Alignment.BottomStart,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Purple80),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Refresh, contentDescription = "",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
                    .clickable { regenerate.invoke() }
            )
            Text(
                text = exerciseModel.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = !isChecked.value },
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}