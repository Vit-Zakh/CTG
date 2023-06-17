package com.leth.ctg.data.repository

import com.leth.ctg.data.api.TrainingsApi
import com.leth.ctg.data.database.dao.TrainingsDao
import com.leth.ctg.data.database.entity.toDomain
import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.dto.toEntity
import com.leth.ctg.data.requests.SaveTrainingsRequest
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.domain.repository.Preferences
import com.leth.ctg.domain.repository.TrainingsRepositoryBE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingsRepositoryImpl(
    private val api: TrainingsApi,
    private val sharedPreferences: Preferences,
    private val trainingsDao: TrainingsDao,
) : TrainingsRepositoryBE {

    override val trainings: Flow<List<TrainingModel>> = trainingsDao.fetchTrainings().map { list ->
        list.map { it.toDomain() }
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

    override suspend fun fetchTrainings(): ApiResult<ResponseWithData<List<TrainingDto>>> {
        return try {
            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
            val response = api.fetchTrainings("Bearer $token")
            trainingsDao.saveTrainings(response.data.map { it.toEntity() })
            ApiResult.Success(data = response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

//    override suspend fun saveTrainings(trainings: List<TrainingSetupModel>): ApiResult<Unit> {
//        return try {
//            val token = sharedPreferences.getToken() ?: return ApiResult.Error()
//            val response = api.saveTrainings(
//                "Bearer $token",
//                SaveTrainingsRequest(trainings.map { it. })
//            )
//            ApiResult.Success()
//        } catch (e: Exception) {
//            ApiResult.Error(e.message ?: "Unknown error")
//        }
//    }

}
