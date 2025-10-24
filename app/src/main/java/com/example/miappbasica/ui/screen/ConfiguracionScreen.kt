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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.miappbasica.R
import com.example.miappbasica.idiomas.LocaleHelper
import com.example.miappbasica.idiomas.LocaleManager
import com.example.miappbasica.ui.theme.LocalThemeState

// ===== COMPOSABLE =====
@Composable
fun ConfiguracionScreen(navController: NavHostController) {

    // ===== ESTADOS =====
    val themeState = LocalThemeState.current
    val isDarkTheme by themeState.isDarkTheme
    var notificaciones by remember { mutableStateOf(true) }
    var sonidoActivado by remember { mutableStateOf(true) }

    // =================================================================
// ===== LÓGICA DE IDIOMA - VERSIÓN FINAL-FINAL-RECONTRAFINAL =====
// =================================================================
// 1. Obtenemos el contexto
    val context = LocalContext.current
//2. Creamos un estado local que podamos modificar AL INSTANTE.
//    Lo inicializamos con el idioma actual del sistema.
    var idiomaSeleccionado by remember {
        mutableStateOf(LocaleManager.languageCode.value)
    }
    // 3. Observamos el StateFlow, pero solo para actualizar nuestro estado local
//    si el idioma cambia desde fuera de esta pantalla.
    LaunchedEffect(Unit) {
        LocaleManager.languageCode.collect { nuevoCodigoSistema ->
            idiomaSeleccionado = nuevoCodigoSistema
        }
    }
    // 4. La función de cambio ahora hace DOS cosas:
//    - Llama al helper para cambiar el idioma de TODA la app.
//    - Actualiza NUESTRO estado local INMEDIATAMENTE para forzar la recomposición.
    val cambiarIdioma = { nuevoCodigoIdioma: String ->
        LocaleHelper.setLocale(context, nuevoCodigoIdioma)
        idiomaSeleccionado = nuevoCodigoIdioma
    }
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
                contentDescription = stringResource(id = R.string.configuracion_titulo),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== TÍTULO =====
        Text(
            text = stringResource(id = R.string.configuracion_titulo),
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
                        text = stringResource(id = R.string.configuracion_tema),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (isDarkTheme) stringResource(id = R.string.tema_modo_nocturno) else stringResource(id = R.string.tema_modo_clasico),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
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
                        text = stringResource(id = R.string.notificaciones),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (notificaciones) stringResource(id = R.string.notificaciones_activadas) else stringResource(id = R.string.notificaciones_desactivadas),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
                    checked = notificaciones,
                    onCheckedChange = { notificaciones = it }
                )
            }
        }

        // ===== CARD 3: IDIOMA (VERSIÓN FINAL) =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.idioma),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // ===== TEXTO DE ESTADO CORREGIDO =====
                Text(
                    text = stringResource(
                        id = R.string.idioma_actual, // "Current language: %1$s" o "Idioma actual: %1$s"
                        stringResource(if (idiomaSeleccionado == "es") R.string.lang_es else R.string.lang_en) // Pasa "Español", "English", etc.
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                // ======================================

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ===== BOTONES CON TEXTO DE RECURSO =====
                    Button(
                        onClick = { cambiarIdioma("es") },
                        enabled = idiomaSeleccionado != "es"
                    ) {
                        Text(stringResource(id = R.string.lang_es)) // Usa el recurso
                    }
                    Button(
                        onClick = { cambiarIdioma("en") },
                        enabled = idiomaSeleccionado != "en"
                    ) {
                        Text(stringResource(id = R.string.lang_en)) // Usa el recurso
                    }
                    // ========================================
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
                        text = stringResource(id = R.string.sonido_vibracion),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (sonidoActivado) stringResource(id = R.string.sonido_activado) else stringResource(id = R.string.sonido_silencio),
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
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.volver_inicio),
                fontSize = 16.sp
            )
        }
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
