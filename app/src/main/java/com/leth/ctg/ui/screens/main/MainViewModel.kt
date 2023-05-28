package com.leth.ctg.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leth.ctg.domain.repository.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            trainingRepository.fetchExercises()
            trainingRepository.fetchPreferences()
            trainingRepository.fetchTrainings()
        }
    }
}
