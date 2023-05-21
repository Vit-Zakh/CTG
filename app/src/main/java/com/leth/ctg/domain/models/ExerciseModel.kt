package com.leth.ctg.domain.models

import com.leth.ctg.utils.TrainingTag

data class ExerciseModel(
    val id: String,
    val type: TrainingTag,
    val title: String,
    val category: String,
)
