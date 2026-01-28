package com.validatech.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.validatech.domain.enums.LabelStatus
import com.validatech.ui.navigation.LocalAppContainer
import com.validatech.util.DateTimeUtils

@Composable
fun DashboardScreen() {
    val repositories = LocalAppContainer.current.repositories
    val labels by repositories.labelRepository.observeByStatus(LabelStatus.ACTIVE).collectAsState(initial = emptyList())

    val expiredToday = labels.filter { it.expiryDateTime.toLocalDate() == java.time.LocalDate.now() }
    val expiringSoon = labels.filter {
        val days = java.time.Duration.between(java.time.LocalDateTime.now(), it.expiryDateTime).toDays()
        days in 1..7
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Painel de Vencimentos", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SummaryCard(title = "Vencidas Hoje", count = expiredToday.size)
                SummaryCard(title = "Em 7 dias", count = expiringSoon.size)
                SummaryCard(title = "Ativas", count = labels.size)
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Vencidas Hoje", style = MaterialTheme.typography.titleMedium)
                if (expiredToday.isEmpty()) {
                    Text("Nenhuma etiqueta vencida hoje.")
                } else {
                    expiredToday.forEach { label ->
                        Card {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(label.shortCode, style = MaterialTheme.typography.labelLarge)
                                Text("Validade: ${DateTimeUtils.formatDateTime(label.expiryDateTime)}")
                                if (label.locationId != null) {
                                    Text("Local: ${label.locationId}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, count: Int) {
    Card(modifier = Modifier.width(110.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Text(count.toString(), style = MaterialTheme.typography.headlineSmall)
        }
    }
}
