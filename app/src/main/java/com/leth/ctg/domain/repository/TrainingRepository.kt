package com.leth.ctg.domain.repository

import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.TrainingItemModel
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.utils.TrainingTag
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    val trainings: Flow<List<TrainingSetupModel>>

    val preferences: Flow<List<TrainingSetupModel>>

    suspend fun fetchPreferences()

    suspend fun fetchTraining(setup: TrainingSetupModel): TrainingModel

    suspend fun addNewTraining()

    suspend fun updateTrainingDetails(training: TrainingSetupModel)

    suspend fun regenerateExercise(exercise: ExerciseModel)
}