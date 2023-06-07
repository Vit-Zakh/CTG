package com.leth.ctg.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.leth.ctg.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeHolderText: String,
    showPassword: Boolean,
    changePasswordVisibility: () -> Unit,
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val passwordVisibilityImage =
                if (showPassword) R.drawable.ic_visible
                else R.drawable.ic_invisible
            IconButton(onClick = {
                changePasswordVisibility()
            }) {
                Icon(
                    painter = painterResource(id = passwordVisibilityImage),
                    contentDescription = ""
                )
            }
        },
    )
}