package com.leth.ctg.domain.models

import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.utils.ExerciseClass
import com.leth.ctg.utils.TrainingType

data class ExerciseModel(
    val id: Long,
    val type: TrainingType,
    val title: String,
    val category: ExerciseClass,
    val isCompleted: Boolean,
)

fun ExerciseModel.toEntity() = ExerciseEntity(
    id = id,
    type = type,
    title = title,
    category = category,
    wasLastTime = isCompleted,
)
