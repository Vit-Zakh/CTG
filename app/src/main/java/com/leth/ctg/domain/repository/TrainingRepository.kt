package com.leth.ctg.domain.repository

import com.leth.ctg.domain.models.TrainingItemModel
import com.leth.ctg.domain.models.TrainingSetupModel
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    val trainings: Flow<List<TrainingItemModel>>

    val preferences: Flow<List<TrainingSetupModel>>
    suspend fun fetchTrainings(): List<TrainingItemModel>
    suspend fun fetchPreferences()
}