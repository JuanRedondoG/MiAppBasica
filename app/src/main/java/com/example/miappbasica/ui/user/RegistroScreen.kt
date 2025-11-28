package com.example.miappbasica.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.miappbasica.ui.viewmodel.LoginUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    uiState: LoginUiState,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRegistroClick: () -> Unit // <-- 1. Recibimos la función aquí
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear Cuenta",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo para el Nombre
        OutlinedTextField(
            value = uiState.nombre,
            onValueChange = onNombreChange, // Sin cambios aquí
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para el Email
        OutlinedTextField(
            value = uiState.email,
            onValueChange = onEmailChange, // Sin cambios aquí
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para la Contraseña
        OutlinedTextField(
            value = uiState.pass,
            onValueChange = onPasswordChange, // Sin cambios aquí
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Mostramos el mensaje de error si existe
        if (uiState.mensajeError != null) {
            Text(
                text = uiState.mensajeError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Botón para Crear Cuenta
        Button(
            // 👇 2. Llamamos a la función del ViewModel cuando se presiona el botón 👇
            onClick = onRegistroClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Crear Cuenta")
        }

        TextButton(
            onClick = { navController.popBackStack() }, // Volver a la pantalla de login
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("¿Ya tienes una cuenta? Inicia Sesión")
        }
    }
}