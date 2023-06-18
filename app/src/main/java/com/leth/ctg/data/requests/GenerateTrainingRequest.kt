package com.leth.ctg.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class GenerateTrainingRequest(
    val prefId: String
)
