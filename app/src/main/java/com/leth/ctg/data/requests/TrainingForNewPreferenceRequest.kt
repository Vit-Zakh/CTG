package com.leth.ctg.data.requests

import com.leth.ctg.data.dto.PreferenceDto
import kotlinx.serialization.Serializable

@Serializable
data class TrainingForNewPreferenceRequest(
    val preference: PreferenceDto
)