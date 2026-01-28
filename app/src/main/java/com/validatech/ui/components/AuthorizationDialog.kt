package com.validatech.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.validatech.data.entities.OperatorEntity
import com.validatech.data.entities.RoleEntity
import com.validatech.domain.enums.PermissionFlag
import com.validatech.util.PasswordHasher
import com.validatech.util.hasPermission
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AuthorizationDialog(
    operators: List<OperatorEntity>,
    roles: List<RoleEntity>,
    requiredPermission: PermissionFlag,
    onDismiss: () -> Unit,
    onAuthorized: (OperatorEntity) -> Unit
) {
    var selectedOperator by remember { mutableStateOf<OperatorEntity?>(null) }
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var attempts by remember { mutableStateOf(0) }
    var cooldownUntil by remember { mutableStateOf(0L) }
    val now = System.currentTimeMillis()
    val inCooldown = now < cooldownUntil

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (inCooldown) return@TextButton
                    val operator = selectedOperator ?: return@TextButton
                    val role = roles.firstOrNull { it.id == operator.roleId }
                    if (role == null || !role.hasPermission(requiredPermission)) {
                        error = "Permissão insuficiente"
                        attempts++
                        if (attempts >= 5) {
                            cooldownUntil = System.currentTimeMillis() + 60_000
                        }
                        return@TextButton
                    }
                    if (!PasswordHasher.verifyPin(pin, operator.pinHash)) {
                        error = "PIN inválido"
                        attempts++
                        if (attempts >= 5) {
                            cooldownUntil = System.currentTimeMillis() + 60_000
                        }
                        return@TextButton
                    }
                    onAuthorized(operator)
                }
            ) {
                Text(if (inCooldown) "Aguarde" else "Autorizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        title = { Text("Autorização necessária") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (inCooldown) {
                    Text("Muitas tentativas. Aguarde 60s.")
                }
                OutlinedTextField(
                    value = selectedOperator?.name ?: "Selecione o operador",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Operador") }
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    operators.forEach { operator ->
                        AssistChip(
                            onClick = { selectedOperator = operator },
                            label = { Text(operator.name) }
                        )
                    }
                }
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    label = { Text("PIN") }
                )
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        }
    )

    if (inCooldown) {
        LaunchedEffect(cooldownUntil) {
            val remaining = cooldownUntil - System.currentTimeMillis()
            if (remaining > 0) {
                delay(remaining)
                attempts = 0
                error = null
            }
        }
    }
}
