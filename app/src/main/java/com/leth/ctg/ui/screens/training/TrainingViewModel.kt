package com.leth.ctg.ui.screens.training

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.repository.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<TrainingScreenState>(TrainingScreenState())

    val state = _state

    fun fetchTraining(id: Long) = viewModelScope.launch {
        Log.d("VZ_TAG", "fetchTraining called")
        _state.value = _state.value.copy(isLoading = true)
        delay(300)
        trainingRepository.fetchExercisesForTraining(id).map {
            _state.value = _state.value.copy(
                isLoading = false,
                training = it.copy()
            )
        }.launchIn(this)
    }

    fun startTraining() {
        _state.value = _state.value.copy(
            isLoading = false,
            isActive = true
        )
    }

    fun completeTraining() {
        _state.value = _state.value.copy(
            isLoading = false,
            isActive = false
        )
    }

    fun regenerateExercise(exercise: ExerciseModel) =
        state.value.training?.let { training ->
            viewModelScope.launch(Dispatchers.IO) {
                trainingRepository.regenerateExercise(exercise, training)
            }
        }

    fun regenerateTraining() = state.value.training?.let { training ->
        viewModelScope.launch {
            trainingRepository.regenerateTraining(training)
        }
    }
}
