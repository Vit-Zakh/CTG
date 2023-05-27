package com.leth.ctg.ui.screens.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.repository.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class SelectionScreenState(
    val list: List<TrainingModel>,
)

@HiltViewModel
class SelectTrainingViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : ViewModel() {

    val state = trainingRepository.trainings.map {
        SelectionScreenState(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = SelectionScreenState(emptyList()),
        started = SharingStarted.WhileSubscribed(5_000),
    )
}
