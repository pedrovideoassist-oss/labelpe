package com.validatech.data.seed

import com.validatech.data.db.AppDatabase
import com.validatech.data.entities.*
import com.validatech.domain.enums.*
import com.validatech.util.PasswordHasher
import java.time.LocalDateTime
import java.util.UUID

object DatabaseSeeder {
    suspend fun seed(database: AppDatabase) {
        val roleDao = database.roleDao()
        val operatorDao = database.operatorDao()
        val locationDao = database.locationDao()
        val categoryDao = database.categoryDao()
        val settingsDao = database.settingsDao()

        if (roleDao.count() > 0) {
            return
        }

        val adminRole = RoleEntity(
            id = UUID.randomUUID().toString(),
            name = "Admin",
            canCreateProduct = true,
            canEditProduct = true,
            canDeleteProduct = true,
            canPrintLabel = true,
            canScanAndWriteOff = true,
            canAccessSettings = true,
            canImportCsv = true,
            canExportReports = true,
            canManageOperators = true,
            canManageLocations = true,
            canManageRoles = true
        )
        val operatorRole = RoleEntity(
            id = UUID.randomUUID().toString(),
            name = "Operador",
            canCreateProduct = true,
            canEditProduct = false,
            canDeleteProduct = false,
            canPrintLabel = true,
            canScanAndWriteOff = true,
            canAccessSettings = false,
            canImportCsv = false,
            canExportReports = false,
            canManageOperators = false,
            canManageLocations = false,
            canManageRoles = false
        )
        roleDao.upsert(adminRole)
        roleDao.upsert(operatorRole)

        val now = LocalDateTime.now()
        val adminPinHash = PasswordHasher.hashPin("2323")
        val operatorPinHash = PasswordHasher.hashPin("1234")

        operatorDao.upsert(
            OperatorEntity(
                id = UUID.randomUUID().toString(),
                name = "Administrador",
                pinHash = adminPinHash,
                roleId = adminRole.id,
                isActive = true,
                createdAt = now,
                updatedAt = now
            )
        )
        operatorDao.upsert(
            OperatorEntity(
                id = UUID.randomUUID().toString(),
                name = "Joana",
                pinHash = operatorPinHash,
                roleId = operatorRole.id,
                isActive = true,
                createdAt = now,
                updatedAt = now
            )
        )
        operatorDao.upsert(
            OperatorEntity(
                id = UUID.randomUUID().toString(),
                name = "Jorge",
                pinHash = adminPinHash,
                roleId = adminRole.id,
                isActive = true,
                createdAt = now,
                updatedAt = now
            )
        )

        locationDao.upsert(LocationEntity(UUID.randomUUID().toString(), "Câmara Fria", true))
        locationDao.upsert(LocationEntity(UUID.randomUUID().toString(), "Estoque Seco", true))

        categoryDao.upsert(CategoryEntity(UUID.randomUUID().toString(), "Carnes", true))
        categoryDao.upsert(CategoryEntity(UUID.randomUUID().toString(), "Laticínios", true))
        categoryDao.upsert(CategoryEntity(UUID.randomUUID().toString(), "Molhos", true))

        settingsDao.upsert(
            RestaurantSettingsEntity(
                restaurantName = "Restaurante Sabor & Arte",
                cnpj = "12.345.678/0001-90",
                address = "Rua das Flores, 123 - Centro",
                labelSize = LabelSize.SIZE_50,
                includeTime = true,
                showBrand = true,
                showRegister = true,
                showLot = true,
                showAddress = true,
                showLocation = true,
                updatedAt = now
            )
        )
    }
}
