package com.validatech.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.validatech.ui.components.BottomNavigationBar
import com.validatech.ui.screens.*

@Composable
fun ValidaTechNavHost() {
    val navController = rememberNavController()
    val appContainer = rememberAppContainer()
    CompositionLocalProvider(LocalAppContainer provides appContainer) {
        var menuExpanded by remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("ValidaTech") },
                    actions = {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Importar CSV") },
                                onClick = {
                                    menuExpanded = false
                                    navController.navigate(NavRoutes.ImportCsv)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Relatórios") },
                                onClick = {
                                    menuExpanded = false
                                    navController.navigate(NavRoutes.Reports)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Configurações") },
                                onClick = {
                                    menuExpanded = false
                                    navController.navigate(NavRoutes.Settings)
                                }
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route) {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavHost(navController = navController, startDestination = NavRoutes.Dashboard) {
                    composable(NavRoutes.Dashboard) { DashboardScreen() }
                    composable(NavRoutes.Products) { ProductsScreen(onAddProduct = { navController.navigate(NavRoutes.ProductForm) }) }
                    composable(NavRoutes.ProductForm) { ProductFormScreen(onDone = { navController.popBackStack() }) }
                    composable(NavRoutes.Print) { PrintScreen() }
                    composable(NavRoutes.WriteOff) { WriteOffScreen() }
                    composable(NavRoutes.ImportCsv) { ImportCsvScreen() }
                    composable(NavRoutes.Reports) { ReportsScreen() }
                    composable(NavRoutes.Settings) { SettingsScreen() }
                }
            }
        }
    }
}
