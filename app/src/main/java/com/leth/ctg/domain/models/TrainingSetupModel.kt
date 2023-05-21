package com.leth.ctg.domain.models

import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.utils.TrainingTag

data class TrainingSetupModel(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val tags: List<TrainingTag>,
    val isEnabled: Boolean,
)

fun TrainingSetupModel.toEntity() = TrainingFormatEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    trainingTags = tags,
    isEnabled = isEnabled,
)
