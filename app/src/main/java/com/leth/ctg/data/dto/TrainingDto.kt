package com.leth.ctg.data.dto

data class TrainingDto(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val exercises: List<String>,
    val templateId: String,
)
