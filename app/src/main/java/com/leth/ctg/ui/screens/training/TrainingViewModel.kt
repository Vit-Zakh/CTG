package com.leth.ctg.ui.screens.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.repository.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<TrainingScreenState>(TrainingScreenState())

    val state = _state

    fun fetchTraining(id: Long) = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        _state.value = _state.value.copy(
            training =
            trainingRepository.fetchTraining(id)
        )
    }

    fun completeTraining() {

    }

    fun regenerateExercise() {

    }

    fun regenerateTraining() {

    }
}
