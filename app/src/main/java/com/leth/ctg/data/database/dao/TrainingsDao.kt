package com.leth.ctg.data.database.dao

import androidx.room.*
import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.data.database.entity.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTraining(exercise: TrainingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrainings(list: List<TrainingEntity>)

    @Query("SELECT * FROM trainings WHERE id = :id")
    suspend fun fetchTraining(id: Long): TrainingEntity

    @Query("SELECT * FROM trainings")
    fun fetchTrainings(): Flow<List<TrainingEntity>>

    @Query("SELECT * FROM trainings WHERE id = :id")
    fun fetchTrainingFlow(id: Long): Flow<TrainingEntity>

    @Query("UPDATE trainings SET exercises = :exercises WHERE id = :id")
    suspend fun updateExercisesList(id: Long, exercises: List<ExerciseEntity>)

}
