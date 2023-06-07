package com.leth.ctg.domain.repository

import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.response.TrainingsResponse
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingSetupModel

interface TrainingsRepositoryBE {

    suspend fun fetchTrainings() : ApiResult<TrainingsResponse>

    suspend fun saveTrainings(trainings: List<TrainingSetupModel>) : ApiResult<Unit>

}