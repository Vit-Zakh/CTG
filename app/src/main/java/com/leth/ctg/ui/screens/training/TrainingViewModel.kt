package com.leth.ctg.ui.screens.training

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.repository.TrainingsRepositoryBE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val trainingsRepositoryBE: TrainingsRepositoryBE
) : ViewModel() {

    private val _state = MutableStateFlow<TrainingScreenState>(TrainingScreenState())

    val state = _state

    fun fetchTraining(id: String) = viewModelScope.launch {
       val result = if (id.toLongOrNull() == null) {
           trainingsRepositoryBE.fetchTraining(id)
       } else {
           trainingsRepositoryBE.savePrefAndFetchTraining(id.toLong())
       }
        Log.d("VZ_TAG", "fetchTraining: $result")
        Log.d("VZ_TAG", "fetchTraining message: ${result.message}")
//        _state.value = _state.value.copy(isLoading = true)
//        delay(300)
//        trainingRepository.fetchExercisesForTraining(id).map {
//            _state.value = _state.value.copy(
//                isLoading = false,
//                training = it,
//                completionProgress = calculateCompletionProgress(it.exercises)
//            )
//        }.launchIn(this)
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

    fun regenerateExercise(exercise: ExerciseModel) =
        state.value.training?.let { training ->
            viewModelScope.launch(Dispatchers.IO) {
//                trainingRepository.regenerateExercise(exercise, training)
            }
        }

    fun regenerateTraining() = state.value.training?.let { training ->
        viewModelScope.launch {
//            trainingRepository.regenerateTraining(training)
        }
    }

    private fun calculateCompletionProgress(exercises: List<ExerciseModel>): Float {
        val listSize = exercises.size
        val completedListSize = exercises.filter { it.isCompleted }.size
        return completedListSize.toFloat() / listSize.toFloat()
    }
}
