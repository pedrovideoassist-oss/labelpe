package com.validatech.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.validatech.domain.enums.LabelStatus
import java.time.LocalDateTime

@Entity(
    tableName = "labels",
    indices = [Index(value = ["shortCode"], unique = true)]
)
data class LabelEntity(
    @PrimaryKey val id: String,
    val shortCode: String,
    val productId: String,
    val printedAt: LocalDateTime,
    val baseDateTime: LocalDateTime,
    val expiryDateTime: LocalDateTime,
    val originalExpiryDate: LocalDateTime?,
    val lot: String?,
    val locationId: String?,
    val responsibleOperatorId: String,
    val status: LabelStatus,
    val writtenOffAt: LocalDateTime?,
    val writtenOffByOperatorId: String?
)
