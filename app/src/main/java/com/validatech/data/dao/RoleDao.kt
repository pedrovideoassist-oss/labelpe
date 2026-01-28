package com.validatech.data.dao

import androidx.room.*
import com.validatech.data.entities.RoleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoleDao {
    @Query("SELECT * FROM roles ORDER BY name")
    fun observeAll(): Flow<List<RoleEntity>>

    @Query("SELECT COUNT(*) FROM roles")
    suspend fun count(): Int

    @Query("SELECT * FROM roles WHERE id = :id")
    suspend fun getById(id: String): RoleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(role: RoleEntity)
}
