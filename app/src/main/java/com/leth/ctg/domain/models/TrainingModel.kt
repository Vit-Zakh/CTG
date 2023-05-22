package com.leth.ctg.domain.models

data class TrainingModel(
    val id: Long,
    val title: String,
    val imageUrl: String?,
    val exercises: List<ExerciseModel>
)
