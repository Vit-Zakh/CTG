package com.leth.ctg.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leth.ctg.data.database.dao.ExercisesDao
import com.leth.ctg.data.database.dao.LocalPreferencesDao
import com.leth.ctg.data.database.dao.PatternsDao
import com.leth.ctg.data.database.dao.TrainingFormatsDao
import com.leth.ctg.data.database.dao.TrainingsDao
import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.data.database.entity.LocalPreferenceEntity
import com.leth.ctg.data.database.entity.PatternEntity
import com.leth.ctg.data.database.entity.TrainingEntity
import com.leth.ctg.data.database.entity.TrainingFormatEntity

@Database(
    entities = [ExerciseEntity::class, TrainingFormatEntity::class, TrainingEntity::class, PatternEntity::class, LocalPreferenceEntity::class],
    version = 1,
    exportSchema = false,
)

@TypeConverters(Converters::class)
abstract class TrainingsDatabase : RoomDatabase() {
    abstract fun exercisesDao(): ExercisesDao
    abstract fun formatsDao(): TrainingFormatsDao
    abstract fun trainingsDao(): TrainingsDao
    abstract fun patternsDao(): PatternsDao
    abstract fun localPreferencesDao(): LocalPreferencesDao
}
