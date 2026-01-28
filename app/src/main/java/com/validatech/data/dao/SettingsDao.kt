package com.validatech.data.dao

import androidx.room.*
import com.validatech.data.entities.RestaurantSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM restaurant_settings WHERE id = 1")
    fun observeSettings(): Flow<RestaurantSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(settings: RestaurantSettingsEntity)
}
