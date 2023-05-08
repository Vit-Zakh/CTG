package com.leth.ctg.domain.models

import android.icu.text.CaseMap.Title
import com.leth.ctg.utils.TrainingTag

data class TrainingSetupModel(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val tags: List<TrainingTag>
)
