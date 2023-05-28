package com.leth.ctg.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leth.ctg.ui.theme.Pink40
import com.leth.ctg.ui.theme.PurpleGrey40

@Composable
fun TrainingProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = Pink40,
    backgroundColor: Color = PurpleGrey40,
    clipShape: Shape = RoundedCornerShape(16.dp)
) {
    Box(
        modifier = modifier
            .clip(clipShape)
            .background(backgroundColor)
            .height(ButtonDefaults.MinHeight)
    ) {
        Box(
            modifier = Modifier
                .background(progressColor)
                .fillMaxHeight()
                .fillMaxWidth(progress)
        )
        Text(
            text = "${progress * 100} %",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth().align(Alignment.Center)
        )
    }
}