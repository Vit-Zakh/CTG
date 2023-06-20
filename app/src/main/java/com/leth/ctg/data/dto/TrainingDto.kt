package com.leth.ctg.data.dto

import com.leth.ctg.data.database.entity.TrainingEntity
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.utils.TrainingType
import kotlinx.serialization.Serializable

@Serializable
data class TrainingDto(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val exercises: List<ExerciseDto>,
)

fun TrainingDto.toEntity(tags: List<TrainingType>) = TrainingEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    exercises = exercises.map { it.toEntity() },
    trainingTypes = tags,
)

fun TrainingDto.toDomain() = TrainingModel(
    id = id,
    title = title,
    imageUrl = imageUrl,
    exercises = exercises.map { it.toDomain() },
)
