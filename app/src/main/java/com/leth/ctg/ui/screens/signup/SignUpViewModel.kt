package com.leth.ctg.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    var state by mutableStateOf(SignUpScreenState())
        private set

    private val resultChannel = Channel<ApiResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun updateUsername(newValue: String) {
        state = state.copy(username = newValue)
    }

    fun updatePassword(newValue: String) {
        state = state.copy(password = newValue)
    }

    fun updateRepeatPassword(newValue: String) {
        state = state.copy(repeatPassword = newValue)
    }

    fun updatePasswordVisibility(newValue: Boolean) {
        state = state.copy(showPassword = newValue)
    }

    fun updateRepeatPasswordVisibility(newValue: Boolean) {
        state = state.copy(showRepeatPassword = newValue)
    }

    fun signUp() {
        val isUsernameValid = isUsernameDataValid()
        val isPasswordValid = isPasswordDataValid()
        val isRepeatPasswordValid = isRepeatPasswordDataValid()
        if (!isUsernameValid || !isPasswordValid || !isRepeatPasswordValid) return
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signUp(state.username, state.password)
            resultChannel.send(result)
        }
    }

    private fun isPasswordDataValid(): Boolean {
        return when {
            state.password.isBlank() -> {
                state = state.copy(passwordError = "This field cannot be blank")
                false
            }

            state.password.length !in 8..15 -> {
                state =
                    state.copy(passwordError = "Password should contain from 8 to 15 characters")
                false
            }

            else -> true
        }
    }

    private fun isRepeatPasswordDataValid(): Boolean {
        return when {
            state.repeatPassword.isBlank() -> {
                state = state.copy(repeatPasswordError = "This field cannot be blank")
                false
            }

            state.password != state.repeatPassword -> {
                state =
                    state.copy(repeatPasswordError = "Passwords do not match")
                false
            }

            else -> true
        }
    }

    private fun isUsernameDataValid(): Boolean {
        return if (state.username.isEmpty()) {
            state = state.copy(usernameError = "This field cannot be blank")
            false
        } else {
            true
        }
    }
}
