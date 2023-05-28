package com.leth.ctg.data.database.dao

import androidx.room.*
import com.leth.ctg.data.database.entity.PatternEntity

@Dao
interface PatternsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePattern(pattern: PatternEntity) : Long

    @Query("SELECT * FROM patterns WHERE id = :id")
    suspend fun fetchPattern(id: Long) : PatternEntity
}
