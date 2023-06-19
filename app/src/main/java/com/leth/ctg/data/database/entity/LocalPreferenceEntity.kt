package com.leth.ctg.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leth.ctg.data.dto.PreferenceDto
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.utils.TrainingType

@Entity(tableName = "local_preferences")
data class LocalPreferenceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val imageUrl: String?,
    val trainingTypes: List<TrainingType>,
    val isEnabled: Boolean = false,
)

fun LocalPreferenceEntity.toDomain() = TrainingSetupModel(
    id = null,
    localId = id,
    title = title,
    imageUrl = imageUrl,
    tags = trainingTypes,
    isEnabled = isEnabled,
)

fun LocalPreferenceEntity.toDto() = PreferenceDto(
    id = null,
    title = title,
    imageUrl = imageUrl,
    trainingTags = trainingTypes,
    isActive = isEnabled,
    templateId = "",
)

fun LocalPreferenceEntity.toEntity(id: String) = TrainingFormatEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    trainingTypes = trainingTypes,
    isEnabled = isEnabled,
)
