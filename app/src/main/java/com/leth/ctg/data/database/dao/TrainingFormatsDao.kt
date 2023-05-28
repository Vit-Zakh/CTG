package com.leth.ctg.data.database.dao

import androidx.room.*
import com.leth.ctg.data.database.entity.TrainingFormatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingFormatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFormat(training: TrainingFormatEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFormats(list: List<TrainingFormatEntity>)

    @Query("SELECT * FROM training_formats")
    fun fetchAllFormats(): Flow<List<TrainingFormatEntity>>

    @Query("SELECT * FROM training_formats WHERE isEnabled = 1")
    fun fetchEnabledFormatsFlow(): Flow<List<TrainingFormatEntity>>

    @Query("SELECT * FROM training_formats WHERE isEnabled = 1")
    suspend fun fetchEnabledFormats(): List<TrainingFormatEntity>

    @Update
    suspend fun update(format: TrainingFormatEntity)

    @Query("UPDATE training_formats SET isEnabled = 1 WHERE id = :id")
    suspend fun changeFormatActivity(id: String)
}
