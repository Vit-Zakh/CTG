package com.leth.ctg.data.dto

import com.leth.ctg.data.database.entity.TrainingEntity
import kotlinx.serialization.Serializable

@Serializable
data class TrainingDto(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val exercises: List<ExerciseDto>,
)

fun TrainingDto.toEntity() = TrainingEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    exercises = exercises.map { it.toEntity() },
)
