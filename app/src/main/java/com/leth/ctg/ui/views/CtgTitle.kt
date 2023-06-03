package com.leth.ctg.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CtgTitle(firstLine: String, secondLine: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = firstLine,
            fontSize = 36.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Thin,
        )
        Text(
            text = secondLine,
            fontSize = 38.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}
