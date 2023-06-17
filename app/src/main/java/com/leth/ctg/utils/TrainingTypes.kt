package com.leth.ctg.utils

import kotlinx.serialization.Serializable

enum class TrainingTypes(val title: String) {
    UNKNOWN("unknown"),
    TYPE_ONE("type_one"),
    TYPE_TWO("type_two"),
    TYPE_THREE("type_three"),
    TYPE_CROSSFIT("type_crossfit"),
}

@Serializable
enum class TrainingType {
    CHEST,
    BACK,
    SHOULDERS,
    ARMS,
    LEGS,
    FULL_BODY,
}

@Serializable
enum class ExerciseClass {
    PRIMARY,
    AUXILIARY,
}
