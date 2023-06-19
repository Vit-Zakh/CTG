package com.leth.ctg.data.dto

import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.utils.ExerciseClass
import com.leth.ctg.utils.TrainingType
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDto(
    val id: String,
    val title: String,
    val imageUrl: String? = null,
    val type: TrainingType,
    val category: ExerciseClass,
    val wasLastTime: Boolean,
    val isReserved: Boolean,
)

fun ExerciseDto.toEntity() = ExerciseEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    type = type,
    category = category,
    wasLastTime = wasLastTime,
    isReserved = isReserved
)

fun ExerciseDto.toDomain() = ExerciseModel(
    id = id,
    title = title,
    imageUrl = imageUrl,
    type = type,
    category = category,
    isCompleted = false,
)