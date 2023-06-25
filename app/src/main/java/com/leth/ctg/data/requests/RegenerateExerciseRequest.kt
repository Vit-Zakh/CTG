package com.leth.ctg.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class RegenerateExerciseRequest(
    val prefId: String,
    val exerciseId: String,
    val currentExercisesIds: List<String>
)
