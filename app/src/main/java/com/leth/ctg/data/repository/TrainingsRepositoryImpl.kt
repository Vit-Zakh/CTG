package com.leth.ctg.data.repository

import android.util.Log
import com.leth.ctg.data.api.TrainingsApi
import com.leth.ctg.data.database.dao.LocalPreferencesDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.dao.TrainingsDao
import com.leth.ctg.data.database.entity.toDomain
import com.leth.ctg.data.database.entity.toDto
import com.leth.ctg.data.database.entity.toEntity
import com.leth.ctg.data.dto.toDomain
import com.leth.ctg.data.dto.toEntity
import com.leth.ctg.data.requests.GenerateTrainingRequest
import com.leth.ctg.data.requests.RegenerateExerciseRequest
import com.leth.ctg.data.requests.TrainingForNewPreferenceRequest
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.TrainingsRepositoryBE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapNotNull

class TrainingsRepositoryImpl(
    private val api: TrainingsApi,
    private val sharedPreferences: Preferences,
    private val trainingsDao: TrainingsDao,
    private val trainingFormatsDao: TrainingFormatsDao,
    private val localPreferencesDao: LocalPreferencesDao,
) : TrainingsRepositoryBE {

    override val trainings: Flow<List<TrainingModel>> = combine(
        trainingFormatsDao.fetchEnabledFormatsFlow(),
        localPreferencesDao.fetchEnabledLocalPreferencesFlow()
    ) { cachedList, localList ->
        (cachedList.map { it.toDomain() } + localList.map { it.toDomain() })
            .map {
                TrainingModel(
                    id = it.id ?: it.localId.toString(),
                    title = it.title,
                    imageUrl = null,
                    exercises = emptyList()
                )
            }
    }

    override suspend fun fetchTraining(prefId: String): ApiResult<TrainingModel> {

        val cachedTraining = trainingsDao.fetchTrainingById(prefId)
//        val hasSameTags =
        val isTrainingFinished = cachedTraining?.exercises?.all { it.isFinished } == true
        if (cachedTraining != null && !isTrainingFinished) {
            return ApiResult.Success(cachedTraining.toDomain())
        }

        val pref = trainingFormatsDao.fetchFormatById(prefId)

        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val response = api.generateTraining(
                "Bearer $token",
                GenerateTrainingRequest(
                    prefId = prefId,
                    currentExercisesIds = emptyMap(),
                )
            )
            val training = response.data
            trainingsDao.addTraining(
                training.toEntity(pref.trainingTypes)
            )
            ApiResult.Success(data = training.toDomain())
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun savePrefAndFetchTraining(preferenceId: Long): ApiResult<TrainingModel> {
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val preference = localPreferencesDao.getPreferenceById(preferenceId)
            val response = api.generateNewTraining(
                "Bearer $token",
                TrainingForNewPreferenceRequest(preference = preference.toDto())
            )
            trainingFormatsDao.addFormat(preference.toEntity(response.data.id))
            localPreferencesDao.removePreferences(preferenceId)
            val training = response.data
            trainingsDao.addTraining(
                training.toEntity(preference.trainingTypes)
            )
            ApiResult.Success(data = training.toDomain())

        } catch (e: Exception) {
            Log.d("VZ_TAG", "error! ${e.message}")
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override fun observeFormatById(id: String): Flow<TrainingModel> {
        return trainingsDao.fetchTrainingFlow(id).mapNotNull { it.toDomain() }
    }

    override suspend fun regenerateExercise(
        prefId: String,
        exerciseId: String,
    ): ApiResult<Unit> {
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val oldTraining = trainingsDao.fetchTrainingById(prefId)
                ?: return ApiResult.Error("No training to update")
            val response = api.regenerateExercise(
                "Bearer $token",
                RegenerateExerciseRequest(
                    prefId = prefId,
                    exerciseId = exerciseId,
                    currentExercisesIds = oldTraining.exercises.map { it.id },
                )
            )
            val exercise = response.data
            val updatedExerciseList = oldTraining.exercises.toMutableList()
            val index = updatedExerciseList.indexOfFirst { it.id == exerciseId }
            updatedExerciseList[index] = exercise.toEntity()
            val newTraining = oldTraining.copy(exercises = updatedExerciseList)
            trainingsDao.updateTraining(newTraining)
            ApiResult.Success()

        } catch (e: Exception) {
            Log.d("VZ_TAG", "error! ${e.message}")
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun regenerateTraining(trainingId: String): ApiResult<Unit> {
        return try {
            Log.d("VZ_TAG", "trying to regenerate training... ")
            val preference = trainingFormatsDao.fetchFormatById(trainingId)
            val oldTraining = trainingsDao.fetchTrainingById(trainingId) ?: return ApiResult.Error()
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()


            val response = api.generateTraining(
                "Bearer $token",
                GenerateTrainingRequest(
                    prefId = trainingId,
                    currentExercisesIds = oldTraining.exercises.groupBy { it.type }
                        .mapValues { (_, values) ->
                            values.map { it.id }
                        }
                )
            )
            val training = response.data
            Log.d("VZ_TAG", "new training: ${training}")
            trainingsDao.updateTraining(
                training.toEntity(preference.trainingTypes)
            )
            ApiResult.Success()
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}
