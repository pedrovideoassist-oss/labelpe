package com.validatech.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.validatech.domain.enums.PermissionFlag
import com.validatech.ui.components.AuthorizationDialog
import com.validatech.ui.navigation.LocalAppContainer

@Composable
fun ReportsScreen() {
    val repositories = LocalAppContainer.current.repositories
    val operators by repositories.operatorRepository.observeActive().collectAsState(initial = emptyList())
    val roles by repositories.roleRepository.observeAll().collectAsState(initial = emptyList())

    var authorized by remember { mutableStateOf(false) }

    if (!authorized) {
        AuthorizationDialog(
            operators = operators,
            roles = roles,
            requiredPermission = PermissionFlag.canExportReports,
            onDismiss = {},
            onAuthorized = { authorized = true }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Relatórios", style = MaterialTheme.typography.titleLarge)
        Text("Escolha o tipo de relatório e exporte CSV via SAF.")
        OutlinedButton(onClick = { /* TODO: export via SAF */ }) {
            Text("Exportar CSV")
        }
    }
}
