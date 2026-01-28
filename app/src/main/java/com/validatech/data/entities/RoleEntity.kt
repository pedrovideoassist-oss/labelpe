package com.validatech.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roles")
data class RoleEntity(
    @PrimaryKey val id: String,
    val name: String,
    val canCreateProduct: Boolean,
    val canEditProduct: Boolean,
    val canDeleteProduct: Boolean,
    val canPrintLabel: Boolean,
    val canScanAndWriteOff: Boolean,
    val canAccessSettings: Boolean,
    val canImportCsv: Boolean,
    val canExportReports: Boolean,
    val canManageOperators: Boolean,
    val canManageLocations: Boolean,
    val canManageRoles: Boolean
)
