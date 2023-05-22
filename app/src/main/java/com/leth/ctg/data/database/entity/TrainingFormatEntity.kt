package com.leth.ctg.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.utils.TrainingTag

@Entity(tableName = "training_formats")
data class TrainingFormatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val imageUrl: String?,
    val trainingTags: List<TrainingTag>,
    val isEnabled: Boolean = false,
)

fun TrainingFormatEntity.toDomain() = TrainingSetupModel(
    id = id,
    title = title,
    imageUrl = imageUrl,
    tags = trainingTags,
    isEnabled = isEnabled,
)
