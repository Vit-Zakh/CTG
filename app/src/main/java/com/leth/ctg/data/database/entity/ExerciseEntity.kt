package com.leth.ctg.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.utils.ExerciseClass
import com.leth.ctg.utils.TrainingType

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val type: TrainingType,
    val title: String,
    val category: ExerciseClass,
    val wasLastTime: Boolean = false,
)

fun ExerciseEntity.toDomain() = ExerciseModel(
    id = id,
    type = type,
    title = title,
    category = category,
)
