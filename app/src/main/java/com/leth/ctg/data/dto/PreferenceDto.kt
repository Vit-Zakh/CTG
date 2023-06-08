package com.leth.ctg.data.dto

data class PreferenceDto(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val trainingTags: List<String>,
    val templateId: String,
    val isActive: Boolean,
)
