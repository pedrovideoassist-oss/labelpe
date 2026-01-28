package com.validatech.data.repository

import com.validatech.data.dao.SettingsDao
import com.validatech.data.entities.RestaurantSettingsEntity
import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val dao: SettingsDao) {
    fun observeSettings(): Flow<RestaurantSettingsEntity?> = dao.observeSettings()

    suspend fun upsert(settings: RestaurantSettingsEntity) = dao.upsert(settings)
}
