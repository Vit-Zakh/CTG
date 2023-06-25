package com.leth.ctg.data.requests

import com.leth.ctg.utils.TrainingType
import kotlinx.serialization.Serializable

@Serializable
data class GenerateTrainingRequest(
    val prefId: String,
    val currentExercisesIds: Map<TrainingType, List<String>>,
)
