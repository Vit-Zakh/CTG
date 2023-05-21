package com.leth.ctg.domain.models

data class TrainingModel(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val exercises: List<ExerciseModel>
)
