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
fun ImportCsvScreen() {
    val repositories = LocalAppContainer.current.repositories
    val operators by repositories.operatorRepository.observeActive().collectAsState(initial = emptyList())
    val roles by repositories.roleRepository.observeAll().collectAsState(initial = emptyList())

    var authorized by remember { mutableStateOf(false) }

    if (!authorized) {
        AuthorizationDialog(
            operators = operators,
            roles = roles,
            requiredPermission = PermissionFlag.canImportCsv,
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
        Text("Importar CSV", style = MaterialTheme.typography.titleLarge)
        Text("Selecione um arquivo CSV via SAF para importar produtos.")
        OutlinedButton(onClick = { /* TODO: SAF launcher */ }) {
            Text("Selecionar arquivo")
        }
        Text("Estrutura esperada: sku,nome_produto,validade_dias,categoria,conservacao,porcao_qtd,porcao_unid,data_base")
    }
}
