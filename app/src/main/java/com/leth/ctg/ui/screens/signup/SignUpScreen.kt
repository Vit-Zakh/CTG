package com.leth.ctg.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.ui.navigation.AUTHENTICATION_ROUTE
import com.leth.ctg.ui.navigation.Screens
import com.leth.ctg.ui.views.PasswordField
import com.leth.ctg.ui.views.UsernameField

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigation: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    modifier: Modifier,
) {

    val context = LocalContext.current

    val state = viewModel.state

    LaunchedEffect(key1 = viewModel) {
        viewModel.authResults.collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    navigation.navigate(Screens.Select.route) {
                        popUpTo(AUTHENTICATION_ROUTE) {
                            inclusive = true
                        }
                    }
                }

                is ApiResult.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            UsernameField(
                value = state.username,
                onValueChanged = { viewModel.updateUsername(it) },
                placeHolderText = "Username",
                errorText = state.usernameError,
            )

            PasswordField(
                value = state.password,
                onValueChanged = { viewModel.updatePassword(it) },
                placeHolderText = "Password",
                showPassword = state.showPassword,
                changePasswordVisibility = { viewModel.updatePasswordVisibility(!state.showRepeatPassword) },
                errorText = state.passwordError,
            )

            PasswordField(
                value = state.repeatPassword,
                onValueChanged = { viewModel.updateRepeatPassword(it) },
                placeHolderText = "Repeat Password",
                showPassword = state.showRepeatPassword,
                changePasswordVisibility = { viewModel.updateRepeatPasswordVisibility(!state.showRepeatPassword) },
                errorText = state.repeatPasswordError,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            Button(
                onClick = { viewModel.signUp() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp)
            ) {
                Text(
                    text = "Sign Up",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
            }
            Spacer(modifier = Modifier.height(ButtonDefaults.MinHeight * 1.5f))
        }
    }
}
