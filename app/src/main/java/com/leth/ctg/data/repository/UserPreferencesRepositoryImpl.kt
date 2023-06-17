package com.leth.ctg.data.repository

import android.util.Log
import com.google.gson.Gson
import com.leth.ctg.data.api.PreferencesApi
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.data.database.entity.toDomain
import com.leth.ctg.data.dto.PreferenceDto
import com.leth.ctg.data.dto.toEntity
import com.leth.ctg.data.requests.SavePreferencesRequest
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.models.toDto
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.UserPreferencesRepositoryBE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class UserPreferencesRepositoryImpl(
    private val api: PreferencesApi,
    private val sharedPreferences: Preferences,
    private val trainingFormatsDao: TrainingFormatsDao,
) : UserPreferencesRepositoryBE {

    override val preferences: Flow<List<TrainingSetupModel>> =
        trainingFormatsDao.fetchAllFormats().map { list ->
            list.map {
                it.toDomain()
            }
        }

    //    private val trainingsDtos = listOf(
//    TrainingDto(
//        id = "1L",
//        title = "Training 1",
//        imageUrl = "",
//        exercises = listOf("Ex1", "Ex2", "Ex3"),
//        templateId = "9901L",
//    ),
//        TrainingDto(
//            id = "2L",
//            title = "Training 2",
//            imageUrl = "",
//            exercises = listOf("Ex1", "Ex2", "Ex3"),
//            templateId = "9901L",
//        ),
//        TrainingDto(
//            id = "3L",
//            title = "Training 3",
//            imageUrl = "",
//            exercises = listOf("Ex1", "Ex2", "Ex3"),
//            templateId = "9901L",
//        ),
//)

    override suspend fun addNewTraining() = trainingFormatsDao
        .addFormat(
            TrainingFormatEntity(
                title = "",
                imageUrl = null,
                trainingTypes = emptyList(),
                isEnabled = false,
            )
        )

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
                Log.d("VZ_TAG", "dto: ${it.toDto()} ")
                it.toDto()
            }
            val a = Gson().toJson(SavePreferencesRequest(preferencesList = preferencesList))
            Log.d("VZ_TAG", "gson: $a")
            val response = api.savePreferences(
                "Bearer $token",
                SavePreferencesRequest(preferencesList = preferencesList)
//                SavePreferencesRequest(emptyList())
            )
            Log.d("VZ_TAG", "preferences update response: $response")
            Log.d("VZ_TAG", "preferences updated")
            ApiResult.Success()
        } catch (e: Exception) {
            Log.d("VZ_TAG", "error! ${e.message}")
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}
