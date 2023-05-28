package com.leth.ctg.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leth.ctg.domain.models.ExerciseModel
import com.leth.ctg.domain.models.ExercisesSetPattern
import com.leth.ctg.domain.models.TrainingModel
import com.leth.ctg.domain.models.TrainingSetupModel
import com.leth.ctg.utils.TrainingType

@Entity(tableName = "patterns")
data class PatternEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val list: List<ExercisesSetPattern>
)
