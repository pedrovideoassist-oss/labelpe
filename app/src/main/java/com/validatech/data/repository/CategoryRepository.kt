package com.validatech.data.repository

import com.validatech.data.dao.CategoryDao
import com.validatech.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val dao: CategoryDao) {
    fun observeAll(): Flow<List<CategoryEntity>> = dao.observeAll()

    suspend fun upsert(category: CategoryEntity) = dao.upsert(category)

    suspend fun delete(category: CategoryEntity) = dao.delete(category)
}
