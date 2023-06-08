package com.leth.ctg.domain.repository

import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingSetupModel

interface TrainingsRepositoryBE {

    suspend fun fetchTrainings() : ApiResult<ResponseWithData<List<TrainingDto>>>

    suspend fun saveTrainings(trainings: List<TrainingSetupModel>) : ApiResult<Unit>

}
