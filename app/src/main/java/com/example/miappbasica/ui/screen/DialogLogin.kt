package com.example.miappbasica.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.miappbasica.ui.viewmodel.LoginUiState

/**
 * Un diálogo de inicio de sesión que es controlado por un ViewModel.
 * Este Composable es "tonto": no tiene estado propio ni lógica de negocio.
 *
 * @param uiState El estado actual de la UI, proporcionado por el ViewModel.
 * @param onDismiss Solicitud para cerrar el diálogo.
 * @param onLoginClick Función a llamar cuando el usuario presiona "Entrar".
 * @param onEmailChange Función para notificar al ViewModel sobre cambios en el campo de email.
 * @param onPasswordChange Función para notificar al ViewModel sobre cambios en el campo de contraseña.
 * @param onLoginSuccess Acción a ejecutar cuando el login es exitoso.
 * @param onResetEvents Función para limpiar el estado del ViewModel (errores, etc.).
 */
@Composable
fun DialogLogin(
    uiState: LoginUiState,
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginSuccess: () -> Unit,
    onResetEvents: () -> Unit
) {
    // LaunchedEffect se usa para reaccionar a cambios en el estado y ejecutar
    // "efectos secundarios" como la navegación o el cierre de un diálogo.
    LaunchedEffect(uiState.loginExitoso) {
        if (uiState.loginExitoso) {
            onLoginSuccess()  // Llama a la acción de éxito (ej. cerrar diálogo y navegar)
            onResetEvents()   // Limpia el estado en el ViewModel para futuros usos
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss, // Cierra si el usuario presiona fuera del diálogo

        title = { Text("Inicio de Sesión") },

        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Campo de Email, controlado por el ViewModel
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo de Contraseña, controlado por el ViewModel
                OutlinedTextField(
                    value = uiState.pass,
                    onValueChange = onPasswordChange,
                    label = { Text("Contraseña") }, // Corregido de "Contrast" a "Contraseña"
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )

                // Mensaje de error, se muestra solo si hay un error en el uiState
                uiState.mensajeError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },

        confirmButton = {
            Button(
                // 🔥 Llama a la función del ViewModel para intentar el login
                onClick = onLoginClick,
                // El botón está habilitado si los campos no están vacíos
                enabled = uiState.email.isNotBlank() && uiState.pass.isNotBlank()
            ) {
                Text("Entrar")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
