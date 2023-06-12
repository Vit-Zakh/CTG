package com.leth.ctg.ui.views

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.UsernameField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeHolderText: String,
    errorText: String,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        ),
        onValueChange = {
            onValueChanged(it)
        },
        placeholder = { if (value.isEmpty()) Text(text = placeHolderText) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    )
    if (errorText.isNotBlank()) {
        Text(
            text = errorText,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .align(Alignment.End)
                .height(20.dp)
                .padding(horizontal = 16.dp),
        )
    } else Spacer(modifier = Modifier.height(20.dp))
}
