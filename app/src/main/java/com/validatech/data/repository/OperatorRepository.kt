package com.validatech.data.repository

import com.validatech.data.dao.OperatorDao
import com.validatech.data.entities.OperatorEntity
import kotlinx.coroutines.flow.Flow

class OperatorRepository(private val dao: OperatorDao) {
    fun observeAll(): Flow<List<OperatorEntity>> = dao.observeAll()

    fun observeActive(): Flow<List<OperatorEntity>> = dao.observeActive()

    suspend fun getById(id: String): OperatorEntity? = dao.getById(id)

    suspend fun upsert(operator: OperatorEntity) = dao.upsert(operator)

    suspend fun delete(operator: OperatorEntity) = dao.delete(operator)
}
