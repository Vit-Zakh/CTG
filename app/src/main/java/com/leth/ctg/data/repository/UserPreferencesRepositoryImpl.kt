package com.leth.ctg.data.repository

import com.leth.ctg.data.api.PreferencesApi
import com.leth.ctg.data.dto.PreferenceDto
import com.leth.ctg.data.requests.SavePreferencesRequest
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.UserPreferencesRepositoryBE

class UserPreferencesRepositoryImpl(
    private val api: PreferencesApi,
    private val preferences: Preferences,
) : UserPreferencesRepositoryBE {

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

    override suspend fun fetchPreferences(): ApiResult<ResponseWithData<List<PreferenceDto>>> {
        return try {
            val token = preferences.getToken() ?: return ApiResult.Error()
            val response = api.fetchPreferences("Bearer $token")
            ApiResult.Success(data = response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun savePreferences(preferencesList: List<TrainingSetupModel>): ApiResult<Unit> {
        return try {
            val token = preferences.getToken() ?: return ApiResult.Error()
            val response = api.savePreferences(
                "Bearer $token",
                SavePreferencesRequest(listOf())
            )
            ApiResult.Success()
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}
