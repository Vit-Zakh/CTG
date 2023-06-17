package com.leth.ctg.ui.screens.preferences

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.repository.UserPreferencesRepositoryBE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private val preferencesRepositoryBE: UserPreferencesRepositoryBE,
//    private val trainingsRepositoryBE: TrainingsRepositoryBE,
) : ViewModel() {

    private var delayedJobForTrainingUpdate: Job? = null


    val state = preferencesRepositoryBE.preferences.map {
        PreferencesScreenState(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = PreferencesScreenState(emptyList()),
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun addNewTraining() = viewModelScope.launch(Dispatchers.IO) {
        preferencesRepositoryBE.addNewTraining()
    }

    fun updateTraining(training: TrainingSetupModel) = viewModelScope.launch(Dispatchers.IO) {
//        preferencesRepositoryBE.updateTrainingDetails(training)
    }

    fun updateTrainingWithDelay(training: TrainingSetupModel) {
        delayedJobForTrainingUpdate?.cancel()
        delayedJobForTrainingUpdate = viewModelScope.launch(Dispatchers.IO) {
            delay(300L)
//            preferencesRepositoryBE.updateTrainingDetails(training)
        }
    }

    //    fun fetchTrainings() = viewModelScope.launch(Dispatchers.IO) {
//        trainingRepository.fetchTrainings()
//    }
    fun savePreferences() {
        Log.d("VZ_TAG", "trying to save prefs: ")
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepositoryBE.savePreferences(state.value.list)
        }
    }
}
