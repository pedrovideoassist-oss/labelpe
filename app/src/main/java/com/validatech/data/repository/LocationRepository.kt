package com.validatech.data.repository

import com.validatech.data.dao.LocationDao
import com.validatech.data.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

class LocationRepository(private val dao: LocationDao) {
    fun observeAll(): Flow<List<LocationEntity>> = dao.observeAll()

    suspend fun upsert(location: LocationEntity) = dao.upsert(location)

    suspend fun delete(location: LocationEntity) = dao.delete(location)
}
