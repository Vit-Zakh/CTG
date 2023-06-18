package com.leth.ctg.domain.models

import com.leth.ctg.data.database.entity.LocalPreferenceEntity
import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.data.dto.PreferenceDto
import com.leth.ctg.utils.TrainingType
import kotlinx.serialization.builtins.NothingSerializer

data class TrainingSetupModel(
    val id: String? = null,
    val localId: Long? = null,
    val title: String,
    val imageUrl: String?,
    val tags: List<TrainingType>,
    val isEnabled: Boolean,
)

fun TrainingSetupModel.toLocalEntity() = if (localId == null) {
    LocalPreferenceEntity(
        title = title,
        imageUrl = imageUrl,
        trainingTypes = tags,
        isEnabled = isEnabled,
    )
} else {
    LocalPreferenceEntity(
        id = localId,
        title = title,
        imageUrl = imageUrl,
        trainingTypes = tags,
        isEnabled = isEnabled,
    )
}

fun TrainingSetupModel.toEntity() =
    TrainingFormatEntity(
        id = requireNotNull(id),
        title = title,
        imageUrl = imageUrl,
        trainingTypes = tags,
        isEnabled = isEnabled,
    )

fun TrainingSetupModel.toDto() = PreferenceDto(
    id = id,
    title = title,
    imageUrl = imageUrl,
    trainingTags = tags,
    isActive = isEnabled,
    templateId = "",
)
