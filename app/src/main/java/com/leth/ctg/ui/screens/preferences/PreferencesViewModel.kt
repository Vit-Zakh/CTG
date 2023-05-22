package com.leth.ctg.ui.screens.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.repository.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PreferencesScreenState(
    val list: List<TrainingSetupModel>,
)

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : ViewModel() {

    val state = trainingRepository.preferences.map {
        PreferencesScreenState(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = PreferencesScreenState(emptyList()),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun addNewTraining() = viewModelScope.launch(Dispatchers.IO) {
        trainingRepository.addNewTraining()
    }
}
