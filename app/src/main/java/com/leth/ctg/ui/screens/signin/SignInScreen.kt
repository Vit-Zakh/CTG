package com.leth.ctg.ui.screens.signin

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.ui.navigation.AUTHENTICATION_ROUTE
import com.leth.ctg.ui.navigation.AuthenticationScreen
import com.leth.ctg.ui.navigation.Screens
import com.leth.ctg.ui.views.TransparentButton

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navigation: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
    modifier: Modifier,
) {

    val context = LocalContext.current

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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = viewModel.username,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
            ),
            onValueChange = {
                viewModel.updateUsername(it)
            },
            placeholder = { if (viewModel.username.isEmpty()) Text(text = "Username") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        TextField(
            value = viewModel.password,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
            ),
            onValueChange = {
                viewModel.updatePassword(it)
            },
            placeholder = { if (viewModel.password.isEmpty()) Text(text = "Password") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

//        TextField(
//            value = titleValue.value,
//            colors = TextFieldDefaults.textFieldColors(
//                containerColor = Color.Transparent,
//            ),
//            onValueChange = {
//                titleValue.value = it
//                delayedTrainingUpdate.invoke(training.copy(title = it))
//            },
//            placeholder = { if (titleValue.value.isEmpty()) Text(text = "Repeat Password") },
//            singleLine = true,
//            modifier = Modifier
//                .fillMaxWidth(),
//        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { viewModel.signIn() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp)
            ) {
                Text(
                    text = "Sign In",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
            }

            TransparentButton(
                onClick = { navigation.navigate(AuthenticationScreen.SignUp.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonDefaults.MinHeight * 1.5f)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp),
            ) {
//                Icon(
//                    imageVector = Icons.Default.Refresh, contentDescription = "",
//                    modifier = Modifier
//                        .padding(8.dp)
//                )
                Text(
                    text = "Sign Up",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }
    }
}
