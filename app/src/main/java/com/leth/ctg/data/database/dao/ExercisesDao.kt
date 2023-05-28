package com.leth.ctg.data.database.dao

import androidx.room.*
import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.data.database.entity.TrainingFormatEntity
import com.leth.ctg.utils.ExerciseClass
import com.leth.ctg.utils.TrainingType
import kotlinx.coroutines.flow.Flow

@Dao
interface ExercisesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExercise(exercise: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveExercises(list: List<ExerciseEntity>)

    @Query("SELECT * FROM exercises WHERE type = :type")
    fun fetchExercise(type: TrainingType): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE type = :type AND category = :category AND id NOT IN (:ignoredIds) ORDER BY RANDOM() LIMIT 1")
    suspend fun regenerateExercise(
        type: TrainingType,
        category: ExerciseClass,
        ignoredIds: List<Long>,
    ): ExerciseEntity

    @Query("SELECT * FROM exercises WHERE type = :type AND category = :category AND id NOT IN (:ignoredIds) ORDER BY RANDOM() LIMIT :amount")
    suspend fun generateExercises(
        type: TrainingType,
        category: ExerciseClass,
        amount: Int,
        ignoredIds: List<Long>,
    ): List<ExerciseEntity>
}
