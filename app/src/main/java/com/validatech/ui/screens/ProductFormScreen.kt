package com.validatech.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.validatech.data.entities.CategoryEntity
import com.validatech.data.entities.ProductEntity
import com.validatech.domain.enums.BaseDateType
import com.validatech.domain.enums.ConservationType
import com.validatech.domain.enums.PortionUnit
import com.validatech.ui.navigation.LocalAppContainer
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.coroutines.launch

@Composable
fun ProductFormScreen(onDone: () -> Unit) {
    val repositories = LocalAppContainer.current.repositories
    val categories by repositories.categoryRepository.observeAll().collectAsState(initial = emptyList())
    val locations by repositories.locationRepository.observeAll().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var sku by remember { mutableStateOf("") }
    var shelfLifeDays by remember { mutableStateOf("1") }
    var portionQty by remember { mutableStateOf("1") }
    var portionUnit by remember { mutableStateOf(PortionUnit.UN) }
    var baseDateType by remember { mutableStateOf(BaseDateType.MANIPULACAO) }
    var conservationType by remember { mutableStateOf(ConservationType.RESFRIADO) }
    var categoryName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<CategoryEntity?>(null) }
    var selectedLocationId by remember { mutableStateOf<String?>(null) }
    var brand by remember { mutableStateOf("") }
    var register by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Novo Produto", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(value = name, onValueChange = { name = it.take(20) }, label = { Text("Nome") })
        OutlinedTextField(value = sku, onValueChange = { sku = it }, label = { Text("SKU") })
        OutlinedTextField(value = shelfLifeDays, onValueChange = { shelfLifeDays = it }, label = { Text("Validade (dias)") })
        OutlinedTextField(value = portionQty, onValueChange = { portionQty = it }, label = { Text("Quantidade") })
        DropdownField("Unidade", portionUnit.name) {
            PortionUnit.values().forEach { unit ->
                DropdownMenuItem(text = { Text(unit.name) }, onClick = { portionUnit = unit })
            }
        }
        DropdownField("Conservação", conservationType.name) {
            ConservationType.values().forEach { type ->
                DropdownMenuItem(text = { Text(type.name) }, onClick = { conservationType = type })
            }
        }
        DropdownField("Data Base", baseDateType.name) {
            BaseDateType.values().forEach { type ->
                DropdownMenuItem(text = { Text(type.name) }, onClick = { baseDateType = type })
            }
        }
        DropdownField("Categoria", selectedCategory?.name ?: "Selecione") {
            categories.forEach { category ->
                DropdownMenuItem(text = { Text(category.name) }, onClick = { selectedCategory = category })
            }
        }
        OutlinedTextField(value = categoryName, onValueChange = { categoryName = it }, label = { Text("Criar categoria (opcional)") })
        DropdownField("Local padrão", selectedLocationId ?: "Sem local") {
            DropdownMenuItem(text = { Text("Sem local") }, onClick = { selectedLocationId = null })
            locations.forEach { location ->
                DropdownMenuItem(text = { Text(location.name) }, onClick = { selectedLocationId = location.id })
            }
        }
        OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Marca / Fornecedor") })
        OutlinedTextField(value = register, onValueChange = { register = it }, label = { Text("Registro Sanitário") })
        OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Observações") })
        Button(onClick = {
            scope.launch {
                val now = LocalDateTime.now()
                val categoryId = selectedCategory?.id ?: if (categoryName.isNotBlank()) {
                    val newCategory = CategoryEntity(UUID.randomUUID().toString(), categoryName, true)
                    repositories.categoryRepository.upsert(newCategory)
                    newCategory.id
                } else {
                    categories.firstOrNull()?.id ?: UUID.randomUUID().toString()
                }
                val product = ProductEntity(
                    id = UUID.randomUUID().toString(),
                    sku = sku,
                    name = name,
                    shelfLifeDays = shelfLifeDays.toIntOrNull() ?: 1,
                    categoryId = categoryId,
                    conservationType = conservationType,
                    portionQty = portionQty.toDoubleOrNull() ?: 1.0,
                    portionUnit = portionUnit,
                    baseDateType = baseDateType,
                    defaultLocationId = selectedLocationId,
                    brandSupplier = brand.ifBlank { null },
                    sanitaryRegister = register.ifBlank { null },
                    notes = notes.ifBlank { null },
                    createdAt = now,
                    updatedAt = now
                )
                repositories.productRepository.upsert(product)
                onDone()
            }
        }) {
            Text("Salvar")
        }
    }
}

@Composable
private fun DropdownField(label: String, value: String, content: @Composable ColumnScope.() -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            content()
        }
        TextButton(onClick = { expanded = true }) { Text("Selecionar") }
    }
}
