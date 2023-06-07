package com.leth.ctg.data.repository

import com.leth.ctg.data.api.TrainingsApi
import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.requests.SaveTrainingsRequest
import com.leth.ctg.data.response.TrainingsResponse
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.TrainingsRepositoryBE

class TrainingsRepositoryImpl(
    private val api: TrainingsApi,
    private val preferences: Preferences,
) : TrainingsRepositoryBE {

    private val trainingsDtos = listOf(
    TrainingDto(
        id = "1L",
        title = "Training 1",
        imageUrl = "",
        exercises = listOf("Ex1", "Ex2", "Ex3"),
        templateId = "9901L",
    ),
        TrainingDto(
            id = "2L",
            title = "Training 2",
            imageUrl = "",
            exercises = listOf("Ex1", "Ex2", "Ex3"),
            templateId = "9901L",
        ),
        TrainingDto(
            id = "3L",
            title = "Training 3",
            imageUrl = "",
            exercises = listOf("Ex1", "Ex2", "Ex3"),
            templateId = "9901L",
        ),
)

    override suspend fun fetchTrainings(): ApiResult<TrainingsResponse> {
        return try {
            val token = preferences.getToken() ?: return ApiResult.Error()
            val response = api.fetchTrainings("Bearer $token")
            ApiResult.Success(data = response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun saveTrainings(trainings: List<TrainingSetupModel>): ApiResult<Unit> {
        return try {
            val token = preferences.getToken() ?: return ApiResult.Error()
            val response = api.saveTrainings(
                "Bearer $token",
                SaveTrainingsRequest(trainingsDtos)
            )
            ApiResult.Success()
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

}