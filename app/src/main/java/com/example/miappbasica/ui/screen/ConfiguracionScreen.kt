package com.example.miappbasica.ui.screen
// Paquete donde se definen las pantallas de la aplicación

// ===== IMPORTS =====
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.miappbasica.ui.theme.LocalThemeState // Importamos el proveedor del estado del tema

// ===== COMPOSABLE =====
@Composable
fun ConfiguracionScreen(navController: NavHostController) {

    // ===== ESTADO =====
    // Obtenemos el estado del tema desde el CompositionLocal.
    val themeState = LocalThemeState.current
    // 'isDarkTheme' se suscribe a los cambios del estado global.
    val isDarkTheme by themeState.isDarkTheme

    // Variables locales para otras configuraciones (estas solo afectan a esta pantalla).
    var notificaciones by remember { mutableStateOf(true) }
    var idiomaSeleccionado by remember { mutableStateOf("Español") }
    var sonidoActivado by remember { mutableStateOf(true) }

    // ===== ESTRUCTURA GENERAL =====
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // ===== ICONO DE CONFIGURACIÓN =====
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Ícono de configuración",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== TÍTULO =====
        Text(
            text = "Configuración de la Aplicación",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== CARD 1: TEMA =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Tema de la Aplicación",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        // El texto ahora lee el estado global 'isDarkTheme'.
                        text = if (isDarkTheme) "Modo Nocturno Activado" else "Modo Clásico Activado",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
                    // El interruptor lee y actualiza el estado global.
                    checked = isDarkTheme,
                    onCheckedChange = { nuevoValor ->
                        themeState.isDarkTheme.value = nuevoValor
                    }
                )
            }
        }

        // ===== CARD 2: NOTIFICACIONES =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Notificaciones",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (notificaciones) "Activadas" else "Desactivadas",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
                    checked = notificaciones,
                    onCheckedChange = { notificaciones = it }
                )
            }
        }

        // ===== CARD 3: IDIOMA =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Idioma",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Idioma actual: $idiomaSeleccionado",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { idiomaSeleccionado = "Español" }) { Text("Español") }
                    Button(onClick = { idiomaSeleccionado = "Inglés" }) { Text("Inglés") }
                }
            }
        }

        // ===== CARD 4: SONIDO =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Sonido / Vibración",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (sonidoActivado) "Sonido Activado" else "Silencio",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
                    checked = sonidoActivado,
                    onCheckedChange = { sonidoActivado = it }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

        // ===== BOTÓN PARA VOLVER AL INICIO =====
        Button(
            onClick = { navController.navigate("inicio") },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
        ) {
            Text("Volver al Inicio", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


/*
📘 Explicación rápida:

1️⃣ Ícono de configuración centrado con fondo circular.
2️⃣ Card 1 → Cambiar entre modo clásico y nocturno.
3️⃣ Card 2 → Activar o desactivar notificaciones.
4️⃣ Card 3 → Cambiar idioma (Español / Inglés).
5️⃣ Card 4 → Activar o desactivar sonido o vibración.
6️⃣ Botón inferior → Vuelve a la pantalla de inicio.
7️⃣ Todo usa Material 3 y se adapta visualmente al resto de la app.
*/
