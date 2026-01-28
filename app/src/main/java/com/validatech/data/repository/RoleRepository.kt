package com.validatech.data.repository

import com.validatech.data.dao.RoleDao
import com.validatech.data.entities.RoleEntity
import kotlinx.coroutines.flow.Flow

class RoleRepository(private val dao: RoleDao) {
    fun observeAll(): Flow<List<RoleEntity>> = dao.observeAll()

    suspend fun getById(id: String): RoleEntity? = dao.getById(id)

    suspend fun upsert(role: RoleEntity) = dao.upsert(role)
}
