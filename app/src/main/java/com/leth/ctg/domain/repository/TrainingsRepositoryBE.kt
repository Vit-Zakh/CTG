package com.leth.ctg.domain.repository

import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.data.dto.ExerciseDto
import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import kotlinx.coroutines.flow.Flow

interface TrainingsRepositoryBE {

    val trainings: Flow<List<TrainingModel>>

//    val activeTrainings: Flow<List<TrainingModel>>

    fun observeFormatById(id: String): Flow<TrainingModel>

//    suspend fun fetchTrainings() : ApiResult<ResponseWithData<List<TrainingDto>>>

    suspend fun fetchTraining(prefId: String) : ApiResult<TrainingModel>
    suspend fun savePrefAndFetchTraining(preferenceId: Long) : ApiResult<TrainingModel>

    suspend fun regenerateExercise(prefId: String, exerciseId: String) : ApiResult<Unit>

//    suspend fun saveTrainings(trainings: List<TrainingSetupModel>) : ApiResult<Unit>

}
