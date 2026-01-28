package com.validatech.data.dao

import androidx.room.*
import com.validatech.data.entities.LabelEntity
import com.validatech.domain.enums.LabelStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {
    @Query("SELECT * FROM labels ORDER BY printedAt DESC")
    fun observeAll(): Flow<List<LabelEntity>>

    @Query("SELECT * FROM labels WHERE shortCode = :shortCode LIMIT 1")
    suspend fun getByShortCode(shortCode: String): LabelEntity?

    @Query("SELECT * FROM labels WHERE status = :status ORDER BY printedAt DESC")
    fun observeByStatus(status: LabelStatus): Flow<List<LabelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(label: LabelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(labels: List<LabelEntity>)
}
