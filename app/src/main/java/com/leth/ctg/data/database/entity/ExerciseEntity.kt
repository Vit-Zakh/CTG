package com.leth.ctg.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leth.ctg.data.dto.ExerciseDto
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.utils.ExerciseClass
import com.leth.ctg.utils.TrainingType

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val type: TrainingType,
    val title: String,
    val imageUrl: String? = null,
    val category: ExerciseClass,
    val wasLastTime: Boolean,
    val isReserved: Boolean,
    val isFinished: Boolean = false,
//    val wasLastTime: Boolean = false,
)

fun ExerciseEntity.toDomain() = ExerciseModel(
    id = id,
    type = type,
    title = title,
    category = category,
    isCompleted = wasLastTime,
)

fun ExerciseEntity.toDto() = ExerciseDto(
    id = id,
    title = title,
    imageUrl = imageUrl,
    type = type,
    category = category,
    wasLastTime = wasLastTime,
    isReserved = isReserved
)
