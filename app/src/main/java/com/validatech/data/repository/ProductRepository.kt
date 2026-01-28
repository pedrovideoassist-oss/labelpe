package com.validatech.data.repository

import com.validatech.data.dao.ProductDao
import com.validatech.data.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val dao: ProductDao) {
    fun observeAll(): Flow<List<ProductEntity>> = dao.observeAll()

    suspend fun getBySku(sku: String): ProductEntity? = dao.getBySku(sku)

    suspend fun upsert(product: ProductEntity) = dao.upsert(product)

    suspend fun delete(product: ProductEntity) = dao.delete(product)
}
