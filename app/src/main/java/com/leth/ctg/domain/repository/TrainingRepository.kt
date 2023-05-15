package com.leth.ctg.domain.repository

import com.leth.ctg.domain.models.TrainingItemModel
import com.leth.ctg.domain.models.TrainingSetupModel

interface TrainingRepository {
    suspend fun fetchTrainings(): List<TrainingItemModel>
    suspend fun fetchPreferences(): List<TrainingSetupModel>
}