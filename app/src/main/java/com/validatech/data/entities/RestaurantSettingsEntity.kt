package com.validatech.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.validatech.domain.enums.LabelSize
import java.time.LocalDateTime

@Entity(tableName = "restaurant_settings")
data class RestaurantSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val restaurantName: String,
    val cnpj: String,
    val address: String?,
    val labelSize: LabelSize,
    val includeTime: Boolean,
    val showBrand: Boolean,
    val showRegister: Boolean,
    val showLot: Boolean,
    val showAddress: Boolean,
    val showLocation: Boolean,
    val updatedAt: LocalDateTime
)
