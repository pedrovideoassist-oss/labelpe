package com.validatech.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.validatech.domain.enums.LabelStatus
import com.validatech.ui.navigation.LocalAppContainer
import com.validatech.util.DateTimeUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WriteOffScreen() {
    val container = LocalAppContainer.current
    val repositories = container.repositories
    val operators by repositories.operatorRepository.observeActive().collectAsState(initial = emptyList())
    val labels by repositories.labelRepository.observeByStatus(LabelStatus.WRITTEN_OFF).collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    var selectedOperatorId by remember { mutableStateOf<String?>(null) }
    var manualCode by remember { mutableStateOf("") }
    var foundLabelId by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Baixar Etiquetas", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Responsável")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            operators.forEach { operator ->
                AssistChip(onClick = { selectedOperatorId = operator.id }, label = { Text(operator.name) })
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            container.scannerService.startScan { code ->
                manualCode = code
            }
        }) {
            Text("Ativar Scanner")
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = manualCode, onValueChange = { manualCode = it }, label = { Text("ShortCode") })
        Button(onClick = {
            scope.launch {
                val label = repositories.labelRepository.getByShortCode(manualCode)
                foundLabelId = label?.id
            }
        }) {
            Text("Buscar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Últimas baixas")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(labels.take(10).size) { index ->
                val label = labels[index]
                Card {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${label.shortCode} • ${label.status}")
                        Text("${DateTimeUtils.formatDateTime(label.writtenOffAt ?: label.printedAt)}")
                    }
                }
            }
        }
    }

    if (foundLabelId != null) {
        AlertDialog(
            onDismissRequest = { foundLabelId = null },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        val label = repositories.labelRepository.getByShortCode(manualCode)
                        val operatorId = selectedOperatorId
                        if (label != null && operatorId != null) {
                            val updated = label.copy(
                                status = LabelStatus.WRITTEN_OFF,
                                writtenOffAt = java.time.LocalDateTime.now(),
                                writtenOffByOperatorId = operatorId
                            )
                            repositories.labelRepository.upsert(updated)
                        }
                        foundLabelId = null
                    }
                }) { Text("Confirmar") }
            },
            dismissButton = { TextButton(onClick = { foundLabelId = null }) { Text("Cancelar") } },
            title = { Text("Confirmar baixa") },
            text = { Text("Deseja baixar a etiqueta $manualCode?") }
        )
    }
}
