package com.leth.ctg.domain.repository

import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import kotlinx.coroutines.flow.Flow

interface TrainingsRepositoryBE {

    val trainings: Flow<List<TrainingModel>>

//    val activeTrainings: Flow<List<TrainingModel>>

    suspend fun fetchTrainings() : ApiResult<ResponseWithData<List<TrainingDto>>>

    suspend fun fetchTraining(prefId: String) : ApiResult<ResponseWithData<TrainingDto>>
    suspend fun savePrefAndFetchTraining(preferenceId: Long) : ApiResult<ResponseWithData<TrainingDto>>

//    suspend fun saveTrainings(trainings: List<TrainingSetupModel>) : ApiResult<Unit>

}
