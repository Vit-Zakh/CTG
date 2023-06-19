package com.leth.ctg.data.repository

import android.util.Log
import com.leth.ctg.data.api.TrainingsApi
import com.leth.ctg.data.database.dao.LocalPreferencesDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.dao.TrainingsDao
import com.leth.ctg.data.database.entity.toDomain
import com.leth.ctg.data.database.entity.toDto
import com.leth.ctg.data.database.entity.toEntity
import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.dto.toEntity
import com.leth.ctg.data.requests.GenerateTrainingRequest
import com.leth.ctg.data.requests.TrainingForNewPreferenceRequest
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.TrainingsRepositoryBE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

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

    override suspend fun fetchTrainings(): ApiResult<ResponseWithData<List<TrainingDto>>> {
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val response = api.fetchTrainings("Bearer $token")
            Log.d("VZ_TAG", "fetchTrainings amount: ${response.data.size}")
            trainingsDao.saveTrainings(response.data.map { it.toEntity() })
            ApiResult.Success(data = response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun fetchTraining(prefId: String): ApiResult<ResponseWithData<TrainingDto>> {
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val response = api.generateTraining(
                "Bearer $token",
                GenerateTrainingRequest(prefId = prefId)
            )
            ApiResult.Success(data = response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun savePrefAndFetchTraining(preferenceId: Long): ApiResult<ResponseWithData<TrainingDto>> {
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val preference = localPreferencesDao.getPreferenceById(preferenceId)
            val response = api.generateNewTraining(
                "Bearer $token",
                TrainingForNewPreferenceRequest(preference = preference.toDto())
            )
            trainingFormatsDao.addFormat(preference.toEntity(response.data.id))
            localPreferencesDao.removePreferences(preferenceId)
            ApiResult.Success(data = response)

        } catch (e: Exception) {
            Log.d("VZ_TAG", "error! ${e.message}")
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}
