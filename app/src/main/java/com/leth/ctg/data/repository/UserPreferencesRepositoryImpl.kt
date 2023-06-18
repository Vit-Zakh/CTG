package com.leth.ctg.data.repository

import android.util.Log
import com.google.gson.Gson
import com.leth.ctg.data.api.PreferencesApi
import com.leth.ctg.data.database.dao.LocalPreferencesDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.entity.LocalPreferenceEntity
import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.data.database.entity.toDomain
import com.leth.ctg.data.dto.PreferenceDto
import com.leth.ctg.data.dto.toEntity
import com.leth.ctg.data.requests.SavePreferenceRequest
import com.leth.ctg.data.requests.SavePreferencesRequest
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.models.toDto
import com.leth.ctg.domain.models.toEntity
import com.leth.ctg.domain.models.toLocalEntity
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.UserPreferencesRepositoryBE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class UserPreferencesRepositoryImpl(
    private val api: PreferencesApi,
    private val sharedPreferences: Preferences,
    private val trainingFormatsDao: TrainingFormatsDao,
    private val localPreferencesDao: LocalPreferencesDao,
) : UserPreferencesRepositoryBE {

    override val preferences: Flow<List<TrainingSetupModel>> = combine(
        trainingFormatsDao.fetchAllFormats(),
        localPreferencesDao.fetchLocalPreferencesFlow()
    ) { fetchedList, cachedList ->
        fetchedList.map { it.toDomain() } + cachedList.map { it.toDomain() }
    }

    override suspend fun addNewTraining() = localPreferencesDao
        .addLocalPreference(
            LocalPreferenceEntity(
                title = "",
                imageUrl = null,
                trainingTypes = emptyList(),
                isEnabled = false,
            )
        )

    override suspend fun updateLocalPreference(training: TrainingSetupModel) = localPreferencesDao
        .update(training.toLocalEntity())

    override suspend fun updateTrainingDetails(training: TrainingSetupModel) {
        if (training.id != null) {
            trainingFormatsDao.update(training.toEntity())
        } else localPreferencesDao.update(training.toLocalEntity())
    }

    override suspend fun fetchPreferences(): ApiResult<ResponseWithData<List<PreferenceDto>>> {
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val response = api.fetchPreferences("Bearer $token")
            Log.d("VZ_TAG", "preferences fetched")
            trainingFormatsDao.saveFormats(response.data.map {
                it.toEntity()
            })
            ApiResult.Success(data = response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun savePreferences(preferencesList: List<TrainingSetupModel>): ApiResult<Unit> {
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val preferencesList = preferencesList.map {
                it.toDto()
            }
            val response = api.savePreferences(
                "Bearer $token",
                SavePreferencesRequest(preferencesList = preferencesList)
            )
            Log.d("VZ_TAG", "preferences updated")
            ApiResult.Success()
        } catch (e: Exception) {
            Log.d("VZ_TAG", "error! ${e.message}")
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun savePreference(preference: TrainingSetupModel): ApiResult<PreferenceDto> {
        Log.d("VZ_TAG", "entering repo with one pref")
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val response = api.savePreference(
                "Bearer $token",
                SavePreferenceRequest(preference = preference.toDto())
            )
            Log.d("VZ_TAG", "preference created")
            preference.localId?.let {
                localPreferencesDao.removePreferences(it)
            }
            trainingFormatsDao.addFormat(response.data.toEntity())
            ApiResult.Success()

        } catch (e: Exception) {
            Log.d("VZ_TAG", "error! ${e.message}")
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}
