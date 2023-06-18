package com.leth.ctg.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.utils.TrainingType

@Entity(tableName = "training_formats")
data class TrainingFormatEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String?,
    val trainingTypes: List<TrainingType>,
    val isEnabled: Boolean = false,
)

fun TrainingFormatEntity.toDomain() = TrainingSetupModel(
    id = id,
    title = title,
    imageUrl = imageUrl,
    tags = trainingTypes,
    isEnabled = isEnabled,
)
