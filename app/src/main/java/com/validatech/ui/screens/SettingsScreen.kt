package com.validatech.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.validatech.data.entities.RestaurantSettingsEntity
import com.validatech.domain.enums.LabelSize
import com.validatech.domain.enums.PermissionFlag
import com.validatech.ui.components.AuthorizationDialog
import com.validatech.ui.components.DropdownSelector
import com.validatech.ui.navigation.LocalAppContainer
import java.time.LocalDateTime
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val repositories = LocalAppContainer.current.repositories
    val operators by repositories.operatorRepository.observeActive().collectAsState(initial = emptyList())
    val roles by repositories.roleRepository.observeAll().collectAsState(initial = emptyList())
    val settings by repositories.settingsRepository.observeSettings().collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    var authorized by remember { mutableStateOf(false) }

    if (!authorized) {
        AuthorizationDialog(
            operators = operators,
            roles = roles,
            requiredPermission = PermissionFlag.canAccessSettings,
            onDismiss = {},
            onAuthorized = { authorized = true }
        )
    }

    var restaurantName by remember { mutableStateOf(settings?.restaurantName ?: "") }
    var cnpj by remember { mutableStateOf(settings?.cnpj ?: "") }
    var address by remember { mutableStateOf(settings?.address ?: "") }
    var labelSize by remember { mutableStateOf(settings?.labelSize ?: LabelSize.SIZE_50) }
    var includeTime by remember { mutableStateOf(settings?.includeTime ?: true) }
    var showBrand by remember { mutableStateOf(settings?.showBrand ?: true) }
    var showRegister by remember { mutableStateOf(settings?.showRegister ?: true) }
    var showLot by remember { mutableStateOf(settings?.showLot ?: true) }
    var showAddress by remember { mutableStateOf(settings?.showAddress ?: true) }
    var showLocation by remember { mutableStateOf(settings?.showLocation ?: true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Configurações", style = MaterialTheme.typography.titleLarge)
        Card {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Dados do Restaurante", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(value = restaurantName, onValueChange = { restaurantName = it }, label = { Text("Nome") })
                OutlinedTextField(value = cnpj, onValueChange = { cnpj = it }, label = { Text("CNPJ") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Endereço") })
            }
        }
        Card {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Configuração da Etiqueta", style = MaterialTheme.typography.titleMedium)
                DropdownSelector("Tamanho", labelSize.name) {
                    LabelSize.values().forEach { size ->
                        DropdownMenuItem(text = { Text(size.name) }, onClick = { labelSize = size })
                    }
                }
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Incluir hora")
                    Switch(checked = includeTime, onCheckedChange = { includeTime = it })
                }
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Mostrar marca")
                    Switch(checked = showBrand, onCheckedChange = { showBrand = it })
                }
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Mostrar registro")
                    Switch(checked = showRegister, onCheckedChange = { showRegister = it })
                }
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Mostrar lote")
                    Switch(checked = showLot, onCheckedChange = { showLot = it })
                }
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Mostrar endereço")
                    Switch(checked = showAddress, onCheckedChange = { showAddress = it })
                }
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Mostrar local")
                    Switch(checked = showLocation, onCheckedChange = { showLocation = it })
                }
            }
        }
        Button(onClick = {
            scope.launch {
                repositories.settingsRepository.upsert(
                    RestaurantSettingsEntity(
                        restaurantName = restaurantName,
                        cnpj = cnpj,
                        address = address.ifBlank { null },
                        labelSize = labelSize,
                        includeTime = includeTime,
                        showBrand = showBrand,
                        showRegister = showRegister,
                        showLot = showLot,
                        showAddress = showAddress,
                        showLocation = showLocation,
                        updatedAt = LocalDateTime.now()
                    )
                )
            }
        }) {
            Text("Salvar configurações")
        }
        Text("Subtelas: Operadores, Perfis/Permissões, Locais, Categorias (CRUD básico)")
    }
}
