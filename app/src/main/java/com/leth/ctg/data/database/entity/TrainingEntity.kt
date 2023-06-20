package com.leth.ctg.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leth.ctg.data.dto.TrainingDto
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.utils.TrainingType

@Entity(tableName = "trainings")
data class TrainingEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String?,
    val trainingTypes: List<TrainingType>,
    val exercises: List<ExerciseEntity>,
)

fun TrainingEntity.toDomain() = TrainingModel(
    id = id,
    title = title,
    imageUrl = imageUrl,
    exercises = exercises.map { it.toDomain() } ?: emptyList()
)

fun TrainingEntity.toDto() = TrainingDto(
    id = id,
    title = title,
    imageUrl = imageUrl,
    exercises = exercises.map { it.toDto() }
)
