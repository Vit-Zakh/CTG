package com.leth.ctg.domain.repository

import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.ExercisesSetPattern
import com.leth.ctg.domain.models.TrainingSetupModel
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    val trainings: Flow<List<TrainingModel>>

    val preferences: Flow<List<TrainingSetupModel>>

    suspend fun fetchPreferences()

    suspend fun fetchTrainings()

    suspend fun fetchExercises()

    suspend fun fetchTraining(id: Long): TrainingModel

    suspend fun addNewTraining()

    suspend fun updateTrainingDetails(training: TrainingSetupModel)

    suspend fun regenerateExercise(exercise: ExerciseModel)

    fun generateTrainingPattern(setup: TrainingFormatEntity): List<ExercisesSetPattern>
}