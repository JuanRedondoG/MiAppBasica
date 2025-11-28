package com.example.miappbasica.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.miappbasica.data.LoginEntity
import com.example.miappbasica.ui.viewmodel.LoginViewModel

@Composable
fun DatabaseDebugScreen(
    viewModel: LoginViewModel,
    navController: NavController // Añadimos NavController para poder navegar
) {
    // Especificamos un valor inicial para evitar problemas de inferencia de tipos
    val users by viewModel.usersList.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contenido de la Base de Datos (LoginEntity)",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Contenedor para la tabla, que ocupa el espacio disponible
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
            ) {
                // Fila de encabezado
                item {
                    TableHeader()
                }

                // Filas de datos para cada usuario
                items(users) { user: LoginEntity ->
                    UserRow(user = user)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 👇👇👇 BOTÓN AÑADIDO PARA VOLVER AL INICIO 👇👇👇
        Button(onClick = {
            // Navega a "inicio" y limpia la pila de navegación hasta esa pantalla
            // para que el usuario no pueda volver aquí con el botón "Atrás" del sistema.
            navController.navigate("inicio") {
                popUpTo("inicio") { inclusive = true }
            }
        }) {
            Text("Volver al Inicio")
        }
    }
}

// --- Implementación de los Componentes que estaban en TODO ---

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TableCell(text = "ID", weight = 0.1f, fontWeight = FontWeight.Bold)
        TableCell(text = "Nombre", weight = 0.25f, fontWeight = FontWeight.Bold)
        TableCell(text = "Email", weight = 0.35f, fontWeight = FontWeight.Bold)
        TableCell(text = "Password", weight = 0.2f, fontWeight = FontWeight.Bold)
        TableCell(text = "Log", weight = 0.1f, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun UserRow(user: LoginEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(text = user.id.toString(), weight = 0.1f)
        TableCell(text = user.nombre, weight = 0.25f)
        TableCell(text = user.email, weight = 0.35f)
        TableCell(text = user.password, weight = 0.2f)
        // Muestra un círculo verde si isLoggedIn es true, rojo si es false
         // val logColor = if (user.isLoggedIn) Color(0xFF4CAF50) else Color(0xFFF44336)

        val logColor = if (user.isLoggedIn == 1) Color(0xFF4CAF50) else Color(0xFFF44336)

        Box(
            modifier = Modifier
                .weight(0.1f)
                .size(12.dp)
                .background(logColor, shape = MaterialTheme.shapes.extraLarge)
        )
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    fontWeight: FontWeight? = null
) {
    Text(
        text = text,
        modifier = Modifier
            .weight(weight)
            .padding(8.dp),
        fontWeight = fontWeight,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall
    )
}
