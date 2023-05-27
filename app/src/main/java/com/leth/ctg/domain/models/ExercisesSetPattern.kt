package com.leth.ctg.domain.models

import com.leth.ctg.utils.ExerciseClass
import com.leth.ctg.utils.TrainingType

data class ExercisesSetPattern(
    val type: TrainingType,
    val category: ExerciseClass,
    val amount: Int,
)
