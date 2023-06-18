package com.leth.ctg.data.database.dao

import androidx.room.*
import com.leth.ctg.data.database.entity.LocalPreferenceEntity
import com.leth.ctg.data.database.entity.TrainingFormatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalPreferencesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocalPreference(preference: LocalPreferenceEntity)

    @Query("DELETE FROM local_preferences WHERE id = :id")
    suspend fun removePreferences(id: Long)

    @Query("SELECT * FROM local_preferences WHERE id = :id")
    suspend fun getPreferenceById(id: Long): LocalPreferenceEntity

    @Query("SELECT * FROM local_preferences")
    fun fetchAllLocalPreferences(): List<TrainingFormatEntity>

    @Query("SELECT * FROM local_preferences")
    fun fetchLocalPreferencesFlow(): Flow<List<LocalPreferenceEntity>>

    @Query("SELECT * FROM local_preferences WHERE isEnabled = 1")
    fun fetchEnabledLocalPreferencesFlow(): Flow<List<LocalPreferenceEntity>>

    @Update
    suspend fun update(preference: LocalPreferenceEntity)

    @Query("UPDATE local_preferences SET isEnabled = 1 WHERE id = :id")
    suspend fun changePreferenceActivity(id: String)
}
