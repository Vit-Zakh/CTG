package com.leth.ctg.data.dto

import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.utils.TrainingType
import kotlinx.serialization.Serializable

@Serializable()
data class PreferenceDto(
    val id: String? = null,
    val title: String,
    val imageUrl: String? = null,
    val trainingTags: List<TrainingType>,
    val templateId: String,
    val isActive: Boolean,
)

fun PreferenceDto.toEntity() = TrainingFormatEntity(
    mongoId = id,
    title = title,
    imageUrl = imageUrl,
    trainingTypes = trainingTags,
    isEnabled = isActive
)
