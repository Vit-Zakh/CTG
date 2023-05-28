package com.leth.ctg.domain.models

import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.utils.TrainingType

data class TrainingSetupModel(
    val id: Long,
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
