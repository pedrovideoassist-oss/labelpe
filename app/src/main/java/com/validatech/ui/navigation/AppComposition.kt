package com.validatech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.validatech.ValidaTechApp
import com.validatech.data.repository.Repositories
import com.validatech.services.PrinterService
import com.validatech.services.ScannerService

data class AppContainer(
    val repositories: Repositories,
    val scannerService: ScannerService,
    val printerService: PrinterService
)

val LocalAppContainer = compositionLocalOf<AppContainer> {
    error("AppContainer not provided")
}

@Composable
fun rememberAppContainer(): AppContainer {
    val app = LocalContext.current.applicationContext as ValidaTechApp
    return remember(app) {
        AppContainer(
            repositories = app.repositories,
            scannerService = app.scannerService,
            printerService = app.printerService
        )
    }
}
