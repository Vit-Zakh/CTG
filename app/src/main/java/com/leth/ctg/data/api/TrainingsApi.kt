package com.leth.ctg.data.api

import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.requests.GenerateTrainingRequest
import com.leth.ctg.data.requests.SaveTrainingsRequest
import com.leth.ctg.data.requests.TrainingForNewPreferenceRequest
import com.leth.ctg.data.response.ResponseWithData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TrainingsApi {
    @POST("save_trainings")
    suspend fun saveTrainings(
        @Header("Authorization") token: String,
        @Body request: SaveTrainingsRequest,
    )

    @GET("my_trainings")
    suspend fun fetchTrainings(
        @Header("Authorization") token: String,
    ) : ResponseWithData<List<TrainingDto>>

    @POST("generate_training")
    suspend fun generateTraining(
        @Header("Authorization") token: String,
        @Body request: GenerateTrainingRequest,
    ) : ResponseWithData<TrainingDto>

    @POST("generate_from_pattern")
    suspend fun generateNewTraining(
        @Header("Authorization") token: String,
        @Body request: TrainingForNewPreferenceRequest,
    ) : ResponseWithData<TrainingDto>
}
