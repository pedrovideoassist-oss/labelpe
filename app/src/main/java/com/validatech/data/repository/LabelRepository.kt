package com.validatech.data.repository

import com.validatech.data.dao.LabelDao
import com.validatech.data.entities.LabelEntity
import com.validatech.domain.enums.LabelStatus
import kotlinx.coroutines.flow.Flow

class LabelRepository(private val dao: LabelDao) {
    fun observeAll(): Flow<List<LabelEntity>> = dao.observeAll()

    fun observeByStatus(status: LabelStatus): Flow<List<LabelEntity>> = dao.observeByStatus(status)

    suspend fun getByShortCode(shortCode: String): LabelEntity? = dao.getByShortCode(shortCode)

    suspend fun upsert(label: LabelEntity) = dao.upsert(label)

    suspend fun upsertAll(labels: List<LabelEntity>) = dao.upsertAll(labels)
}
