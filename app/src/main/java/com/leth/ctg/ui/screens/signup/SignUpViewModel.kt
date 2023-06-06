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

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val resultChannel = Channel<ApiResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun updateUsername(newValue: String) {
        username = newValue
    }

    fun updatePassword(newValue: String) {
        password = newValue
    }

    fun signUp() = viewModelScope.launch(Dispatchers.IO) {
        val result = authRepository.signUp(username, password)
        resultChannel.send(result)
    }
}
