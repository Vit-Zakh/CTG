package com.leth.ctg.ui.screens.training

import com.leth.ctg.domain.models.TrainingModel

data class TrainingScreenState(
    val isLoading: Boolean = true,
    val training: TrainingModel? = null,
)
