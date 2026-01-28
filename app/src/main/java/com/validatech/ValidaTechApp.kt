package com.validatech

import android.app.Application
import com.validatech.data.db.AppDatabase
import com.validatech.data.repository.*
import com.validatech.services.FakePrinterService
import com.validatech.services.FakeScannerService
import com.validatech.services.PrinterService
import com.validatech.services.ScannerService

class ValidaTechApp : Application() {
    lateinit var database: AppDatabase
        private set
    lateinit var repositories: Repositories
        private set
    lateinit var scannerService: ScannerService
        private set
    lateinit var printerService: PrinterService
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.build(this)
        repositories = Repositories(
            operatorRepository = OperatorRepository(database.operatorDao()),
            roleRepository = RoleRepository(database.roleDao()),
            locationRepository = LocationRepository(database.locationDao()),
            categoryRepository = CategoryRepository(database.categoryDao()),
            productRepository = ProductRepository(database.productDao()),
            labelRepository = LabelRepository(database.labelDao()),
            settingsRepository = SettingsRepository(database.settingsDao())
        )
        scannerService = FakeScannerService()
        printerService = FakePrinterService()
    }
}
