package com.validatech.data.dao

import androidx.room.*
import com.validatech.data.entities.OperatorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OperatorDao {
    @Query("SELECT * FROM operators ORDER BY name")
    fun observeAll(): Flow<List<OperatorEntity>>

    @Query("SELECT * FROM operators WHERE isActive = 1 ORDER BY name")
    fun observeActive(): Flow<List<OperatorEntity>>

    @Query("SELECT * FROM operators WHERE id = :id")
    suspend fun getById(id: String): OperatorEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(operator: OperatorEntity)

    @Delete
    suspend fun delete(operator: OperatorEntity)
}
