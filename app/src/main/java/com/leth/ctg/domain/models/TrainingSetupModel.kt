package com.leth.ctg.domain.models

import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.data.dto.PreferenceDto
import com.leth.ctg.utils.TrainingType

data class TrainingSetupModel(
    val id: Long,
    val mongoId: String? = null,
    val title: String,
    val imageUrl: String?,
    val tags: List<TrainingType>,
    val isEnabled: Boolean,
)

fun TrainingSetupModel.toEntity() = TrainingFormatEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    trainingTypes = tags,
    isEnabled = isEnabled,
)

fun TrainingSetupModel.toDto() = PreferenceDto(
    id = mongoId,
    title = title,
    imageUrl = imageUrl,
    trainingTags = tags,
    isActive = isEnabled,
    templateId = "",
)
