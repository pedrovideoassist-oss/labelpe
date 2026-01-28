package com.validatech.util

import com.validatech.data.entities.RoleEntity
import com.validatech.domain.enums.PermissionFlag

fun RoleEntity.hasPermission(flag: PermissionFlag): Boolean {
    return when (flag) {
        PermissionFlag.canCreateProduct -> canCreateProduct
        PermissionFlag.canEditProduct -> canEditProduct
        PermissionFlag.canDeleteProduct -> canDeleteProduct
        PermissionFlag.canPrintLabel -> canPrintLabel
        PermissionFlag.canScanAndWriteOff -> canScanAndWriteOff
        PermissionFlag.canAccessSettings -> canAccessSettings
        PermissionFlag.canImportCsv -> canImportCsv
        PermissionFlag.canExportReports -> canExportReports
        PermissionFlag.canManageOperators -> canManageOperators
        PermissionFlag.canManageLocations -> canManageLocations
        PermissionFlag.canManageRoles -> canManageRoles
    }
}
