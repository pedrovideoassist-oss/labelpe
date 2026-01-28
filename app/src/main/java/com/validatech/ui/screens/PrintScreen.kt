package com.validatech.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.validatech.data.entities.LabelEntity
import com.validatech.data.entities.ProductEntity
import com.validatech.domain.enums.LabelStatus
import com.validatech.ui.components.DropdownSelector
import com.validatech.ui.navigation.LocalAppContainer
import com.validatech.util.DateTimeUtils
import com.validatech.util.ShortCodeGenerator
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PrintScreen() {
    val container = LocalAppContainer.current
    val repositories = container.repositories
    val operators by repositories.operatorRepository.observeActive().collectAsState(initial = emptyList())
    val products by repositories.productRepository.observeAll().collectAsState(initial = emptyList())
    val locations by repositories.locationRepository.observeAll().collectAsState(initial = emptyList())
    val settings by repositories.settingsRepository.observeSettings().collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    var selectedProduct by remember { mutableStateOf<ProductEntity?>(null) }
    var selectedOperatorId by remember { mutableStateOf<String?>(null) }
    var lot by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(1) }
    var selectedLocationId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Gerar / Imprimir", style = MaterialTheme.typography.titleLarge) }
        item {
            Text("Responsável", style = MaterialTheme.typography.titleMedium)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                operators.forEach { operator ->
                    AssistChip(
                        onClick = { selectedOperatorId = operator.id },
                        label = { Text(operator.name) }
                    )
                }
            }
        }
        item {
            Text("Selecione o Produto", style = MaterialTheme.typography.titleMedium)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                products.forEach { product ->
                    ListItem(
                        headlineContent = { Text(product.name) },
                        supportingContent = { Text("${product.sku} • ${product.shelfLifeDays} dias") },
                        trailingContent = {
                            RadioButton(
                                selected = selectedProduct?.id == product.id,
                                onClick = {
                                    selectedProduct = product
                                    selectedLocationId = product.defaultLocationId
                                }
                            )
                        }
                    )
                }
            }
        }
        item {
            OutlinedTextField(value = lot, onValueChange = { lot = it }, label = { Text("Lote (opcional)") })
        }
        item {
            DropdownSelector("Local (opcional)", selectedLocationId ?: "Sem local") {
                DropdownMenuItem(text = { Text("Sem local") }, onClick = { selectedLocationId = null })
                locations.forEach { location ->
                    DropdownMenuItem(text = { Text(location.name) }, onClick = { selectedLocationId = location.id })
                }
            }
        }
        item {
            DropdownSelector("Quantidade", quantity.toString()) {
                listOf(1, 2, 3, 5, 10).forEach { value ->
                    DropdownMenuItem(text = { Text(value.toString()) }, onClick = { quantity = value })
                }
            }
        }
        item {
            Button(onClick = {
                val product = selectedProduct ?: return@Button
                val operatorId = selectedOperatorId ?: return@Button
                val settingsValue = settings ?: return@Button
                scope.launch {
                    val now = DateTimeUtils.nowWithOptionalTime(settingsValue.includeTime)
                    val labels = (1..quantity).map {
                        val shortCode = ShortCodeGenerator.generate()
                        LabelEntity(
                            id = UUID.randomUUID().toString(),
                            shortCode = shortCode,
                            productId = product.id,
                            printedAt = now,
                            baseDateTime = now,
                            expiryDateTime = now.plusDays(product.shelfLifeDays.toLong()),
                            originalExpiryDate = null,
                            lot = lot.ifBlank { null },
                            locationId = selectedLocationId,
                            responsibleOperatorId = operatorId,
                            status = LabelStatus.ACTIVE,
                            writtenOffAt = null,
                            writtenOffByOperatorId = null
                        )
                    }
                    repositories.labelRepository.upsertAll(labels)
                    labels.forEach { label ->
                        container.printerService.printLabel(label, settingsValue) {}
                    }
                }
            }) {
                Text("IMPRIMIR")
            }
        }
    }
}
