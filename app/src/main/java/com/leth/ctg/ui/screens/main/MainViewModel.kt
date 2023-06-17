package com.leth.ctg.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.repository.AuthRepository
import com.leth.ctg.domain.repository.TrainingsRepositoryBE
import com.leth.ctg.domain.repository.UserPreferencesRepositoryBE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trainingRepositoryBE: TrainingsRepositoryBE,
    private val userPreferencesRepositoryBE: UserPreferencesRepositoryBE,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var state by mutableStateOf(MainScreenState(isLoading = true))
        private set

    private val resultChannel = Channel<ApiResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.authenticate()
            delay(400)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    fun fetchDataForLoggedInUser() = viewModelScope.launch(Dispatchers.IO) {
        trainingRepositoryBE.fetchTrainings()
        userPreferencesRepositoryBE.fetchPreferences()
    }
}
