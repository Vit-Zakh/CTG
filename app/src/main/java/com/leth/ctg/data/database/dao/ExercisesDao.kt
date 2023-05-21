package com.leth.ctg.data.database.dao

import androidx.room.*
import com.leth.ctg.data.database.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExercisesDao {

    @Query("SELECT * FROM exercises WHERE type = :type")
    fun fetchExercise(type: String): Flow<List<ExerciseEntity>>
}
