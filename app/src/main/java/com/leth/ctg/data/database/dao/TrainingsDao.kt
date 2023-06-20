package com.leth.ctg.data.database.dao

import androidx.room.*
import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.data.database.entity.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTraining(exercise: TrainingEntity)

    @Query("SELECT * FROM trainings WHERE id = :id")
    fun fetchTrainingById(id: String): TrainingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrainings(list: List<TrainingEntity>)


    @Query("SELECT * FROM trainings")
    fun fetchTrainings(): Flow<List<TrainingEntity>>

    @Query("SELECT * FROM trainings WHERE id IN (:ids)")
    fun fetchTrainings(ids: List<String>): List<TrainingEntity>

    @Query("SELECT * FROM trainings WHERE id = :id")
    fun fetchTrainingFlow(id: String): Flow<TrainingEntity>

    @Query("DELETE FROM trainings")
    suspend fun deleteTrainings()

}
