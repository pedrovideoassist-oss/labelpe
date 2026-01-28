package com.validatech.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.validatech.data.entities.ProductEntity
import com.validatech.domain.enums.PermissionFlag
import com.validatech.ui.components.AuthorizationDialog
import com.validatech.ui.navigation.LocalAppContainer

@Composable
fun ProductsScreen(onAddProduct: () -> Unit) {
    val repositories = LocalAppContainer.current.repositories
    val products by repositories.productRepository.observeAll().collectAsState(initial = emptyList())
    val operators by repositories.operatorRepository.observeActive().collectAsState(initial = emptyList())
    val roles by repositories.roleRepository.observeAll().collectAsState(initial = emptyList())

    var search by remember { mutableStateOf("") }
    var authRequest by remember { mutableStateOf<PermissionFlag?>(null) }

    val filtered = products.filter { it.name.contains(search, ignoreCase = true) || it.sku.contains(search, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Produtos", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onAddProduct) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Novo Produto")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Buscar por nome ou SKU") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filtered.size) { index ->
                val product = filtered[index]
                ProductCard(product = product, onEdit = { authRequest = PermissionFlag.canEditProduct }, onDelete = { authRequest = PermissionFlag.canDeleteProduct })
            }
        }
    }

    authRequest?.let { permission ->
        AuthorizationDialog(
            operators = operators,
            roles = roles,
            requiredPermission = permission,
            onDismiss = { authRequest = null },
            onAuthorized = { authRequest = null }
        )
    }
}

@Composable
private fun ProductCard(product: ProductEntity, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Text(product.sku, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text(product.conservationType.name) })
                AssistChip(onClick = {}, label = { Text("${product.shelfLifeDays} dias") })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = {}) {
                    Icon(Icons.Default.Print, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Imprimir")
                }
                TextButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar")
                }
                TextButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Excluir")
                }
            }
        }
    }
}
