package com.leth.ctg.data.api

import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.requests.SaveTrainingsRequest
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

    @GET("trainings")
    suspend fun fetchTrainings(
        @Header("Authorization") token: String,
    ) : ResponseWithData<List<TrainingDto>>
}
