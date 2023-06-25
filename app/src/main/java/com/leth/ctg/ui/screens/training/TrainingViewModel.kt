package com.leth.ctg.ui.screens.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.repository.TrainingsRepositoryBE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val trainingsRepositoryBE: TrainingsRepositoryBE,
) : ViewModel() {

    private val _state = MutableStateFlow<TrainingScreenState>(TrainingScreenState())

    val state = _state

    fun fetchTraining(id: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = if (id.toLongOrNull() == null) {
            trainingsRepositoryBE.fetchTraining(id)
        } else {
            trainingsRepositoryBE.savePrefAndFetchTraining(id.toLong())
        }
        if (result is ApiResult.Success) {
            trainingsRepositoryBE.observeFormatById(requireNotNull(result.data?.id)).map {
                _state.value = _state.value.copy(
                    training = it,
                    isLoading = false
                )
            }.stateIn(this)
        }
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

    fun completeExercise(exerciseId: String) = viewModelScope.launch(Dispatchers.IO) {
//        trainingRepository.completeExercise(exerciseId)
        val updatedExercisesList = _state.value.training?.exercises?.toMutableList()
        updatedExercisesList?.let { list ->
            val indexToUpdate = list.indexOfFirst { it.id == exerciseId }
            val valueToUpdate = !list[indexToUpdate].isCompleted
            val exerciseToUpdate = list[indexToUpdate].copy(isCompleted = valueToUpdate)
            updatedExercisesList[indexToUpdate] = exerciseToUpdate
            val updatedTraining = _state.value.training?.copy(exercises = updatedExercisesList)
            _state.value = _state.value.copy(
                training = updatedTraining,
                completionProgress = calculateCompletionProgress(updatedExercisesList)
            )
        }
    }

    fun regenerateExercise(prefId: String, exercise: ExerciseModel) =
        state.value.training?.let { training ->
            viewModelScope.launch(Dispatchers.IO) {
                trainingsRepositoryBE.regenerateExercise(
                    prefId = prefId,
                    exerciseId = exercise.id,
                )
            }
        }

    fun regenerateTraining() = state.value.training?.let { training ->
        viewModelScope.launch {
            trainingsRepositoryBE.regenerateTraining(training.id)
        }
    }

    private fun calculateCompletionProgress(exercises: List<ExerciseModel>): Float {
        val listSize = exercises.size
        val completedListSize = exercises.filter { it.isCompleted }.size
        return completedListSize.toFloat() / listSize.toFloat()
    }
}
