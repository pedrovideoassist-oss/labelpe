package com.validatech.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.validatech.ui.navigation.NavRoutes

private data class NavItem(val route: String, val label: String, val icon: ImageVector)

@Composable
fun BottomNavigationBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    val items = listOf(
        NavItem(NavRoutes.Dashboard, "Dashboard", Icons.Default.Home),
        NavItem(NavRoutes.Products, "Produtos", Icons.Default.Inventory),
        NavItem(NavRoutes.Print, "Imprimir", Icons.Default.Print),
        NavItem(NavRoutes.WriteOff, "Baixar", Icons.Default.QrCodeScanner)
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = { androidx.compose.material3.Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
