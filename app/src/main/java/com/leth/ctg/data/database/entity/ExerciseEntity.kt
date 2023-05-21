package com.leth.ctg.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    val type: String,
    val title: String,
    val category: String,
    val wasLastTime: Boolean = false,
)
