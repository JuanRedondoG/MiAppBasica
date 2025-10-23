package com.example.miappbasica.ui.screen
// Paquete donde se definen las pantallas de la aplicación

// ===== IMPORTS =====
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource // <-- 1. IMPORTANTE
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.miappbasica.R // <-- 2. IMPORTANTE

// ===== COMPOSABLE =====
@Composable
fun AcercaDeScreen(navController: NavHostController) {

    // Estructura principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // ===== ICONO DE INFORMACIÓN =====
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                // ANTES: contentDescription = "Ícono de información",
                contentDescription = stringResource(id = R.string.about_titulo), // <-- DESPUÉS
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== TÍTULO =====
        Text(
            // ANTES: text = "Acerca de esta aplicación",
            text = stringResource(id = R.string.about_titulo), // <-- DESPUÉS
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== CARD 1: NOMBRE Y VERSIÓN =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    // ANTES: text = "Mi Aplicación Genérica",
                    text = stringResource(id = R.string.about_app_titulo), // <-- DESPUÉS
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    // ANTES: text = "Versión 1.0.0",
                    text = stringResource(id = R.string.about_app_version), // <-- DESPUÉS
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    // ANTES: text = "Última actualización: Octubre 2025",
                    text = stringResource(id = R.string.about_app_actualizacion), // <-- DESPUÉS
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // ===== CARD 2: DESARROLLADOR =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    // ANTES: text = "Desarrollador",
                    text = stringResource(id = R.string.about_desarrollador_titulo), // <-- DESPUÉS
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider()
                Text(
                    // ANTES: text = "Profesor Jorge Niochet",
                    text = stringResource(id = R.string.about_desarrollador_nombre), // <-- DESPUÉS
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    // ANTES: text = "Escuela de Informática — Duoc UC San Joaquín",
                    text = stringResource(id = R.string.about_desarrollador_escuela), // <-- DESPUÉS
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // ===== CARD 3: DESCRIPCIÓN =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    // ANTES: text = "Descripción",
                    text = stringResource(id = R.string.about_descripcion_titulo), // <-- DESPUÉS
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider()
                Text(
                    // ANTES: text = "Esta aplicación fue creada con Jetpack Compose...",
                    text = stringResource(id = R.string.about_descripcion_texto), // <-- DESPUÉS
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }

        // ===== CARD 4: CONTACTO =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    // ANTES: text = "Contacto",
                    text = stringResource(id = R.string.about_contacto_titulo), // <-- DESPUÉS
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider()
                Text(
                    // ANTES: "📧 Email: contacto@duocuc.cl",
                    text = "📧 ${stringResource(id = R.string.about_contacto_email)}", // <-- DESPUÉS con formato
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    // ANTES: "🌐 Web: www.duoc.cl",
                    text = "🌐 ${stringResource(id = R.string.about_contacto_web)}", // <-- DESPUÉS con formato
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    // ANTES: "📍 Ubicación: San Joaquín, Santiago - Chile",
                    text = "📍 ${stringResource(id = R.string.about_contacto_ubicacion)}", // <-- DESPUÉS con formato
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== BOTÓN PARA VOLVER AL INICIO =====
        Button(
            onClick = { navController.navigate("inicio") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
        ) {
            Text(
                // ANTES: text = "Volver al inicio",
                text = stringResource(id = R.string.about_volver_inicio), // <-- DESPUÉS
                fontSize = 16.sp
            )
        }
    }
}


/*
📘 Explicación rápida:

1️⃣ Ícono de información grande y centrado.
2️⃣ Card 1 → nombre de la app y versión.
3️⃣ Card 2 → información del desarrollador.
4️⃣ Card 3 → descripción o propósito de la app.
5️⃣ Card 4 → medios de contacto o redes sociales.
6️⃣ Botón inferior → regresa al inicio.
7️⃣ Todo sigue el mismo formato visual (espaciado, colores, esquinas redondeadas).
*/
