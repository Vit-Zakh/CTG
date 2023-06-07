package com.leth.ctg.ui.screens.selection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.repository.TrainingRepository
import com.leth.ctg.domain.repository.TrainingsRepositoryBE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SelectionScreenState(
    val list: List<TrainingModel>,
)

@HiltViewModel
class SelectTrainingViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val testRepo: TrainingsRepositoryBE,
) : ViewModel() {

    val state = trainingRepository.trainings.map {
        SelectionScreenState(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = SelectionScreenState(emptyList()),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun test1() = viewModelScope.launch(Dispatchers.IO) {
        val result = testRepo.saveTrainings(listOf())
        Log.d("VZ_TAG", "test1: ${result} ")
    }

    fun test2() = viewModelScope.launch(Dispatchers.IO) {
        val result = testRepo.fetchTrainings()
        Log.d("VZ_TAG", "test2: ${result.data}")
    }
}
