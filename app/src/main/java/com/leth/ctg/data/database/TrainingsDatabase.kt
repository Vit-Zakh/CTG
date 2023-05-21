package com.leth.ctg.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leth.ctg.data.database.dao.ExercisesDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.data.database.entity.TrainingFormatEntity

@Database(
    entities = [ExerciseEntity::class, TrainingFormatEntity::class],
    version = 1,
    exportSchema = false,
)

@TypeConverters(Converters::class)
abstract class TrainingsDatabase : RoomDatabase() {
    abstract fun exercisesDao(): ExercisesDao
    abstract fun formatsDao(): TrainingFormatsDao
}
