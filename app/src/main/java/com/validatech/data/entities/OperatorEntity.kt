package com.validatech.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "operators")
data class OperatorEntity(
    @PrimaryKey val id: String,
    val name: String,
    val pinHash: String,
    val roleId: String,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
