package com.leth.ctg.domain.repository

import com.leth.ctg.data.dto.PreferenceDto
import com.leth.ctg.data.response.ResponseWithData
import com.leth.ctg.domain.models.ApiResult
import com.leth.ctg.domain.models.TrainingSetupModel
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepositoryBE {

    val preferences: Flow<List<TrainingSetupModel>>

    suspend fun addNewTraining()

    suspend fun fetchPreferences() : ApiResult<ResponseWithData<List<PreferenceDto>>>

    suspend fun savePreferences(preferencesList: List<TrainingSetupModel>) : ApiResult<Unit>

}
