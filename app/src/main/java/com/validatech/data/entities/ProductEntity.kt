package com.validatech.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.validatech.domain.enums.BaseDateType
import com.validatech.domain.enums.ConservationType
import com.validatech.domain.enums.PortionUnit
import java.time.LocalDateTime

@Entity(
    tableName = "products",
    indices = [Index(value = ["sku"], unique = true)]
)
data class ProductEntity(
    @PrimaryKey val id: String,
    val sku: String,
    val name: String,
    val shelfLifeDays: Int,
    val categoryId: String,
    val conservationType: ConservationType,
    val portionQty: Double,
    val portionUnit: PortionUnit,
    val baseDateType: BaseDateType,
    val defaultLocationId: String?,
    val brandSupplier: String?,
    val sanitaryRegister: String?,
    val notes: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
