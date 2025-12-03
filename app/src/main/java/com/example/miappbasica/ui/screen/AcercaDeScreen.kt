package com.example.miappbasica.ui.screen
// Paquete donde se definen las pantallas de la aplicación

// ===== IMPORTS =====
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.miappbasica.R

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
            Image(
                painter = painterResource(id = R.drawable.comic_banner_placeholder),
                contentDescription = stringResource(id = R.string.home_nuevo),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ===== TÍTULO DE LA APLICACION=====
        Text(
            text = "Acerca de The Adventures of... App",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== CARD 1: NOMBRE Y VERSIÓN =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "The Adventures of...",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Versión 1.0.1",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Última actualización: Octubre 2025",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // ===== CARD 2: DESARROLLADORES (CORREGIDA) =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            // Se usa UNA SOLA Column para organizar todo el contenido interno.
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Desarrolladores",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Desarrollador 1
                Text(text = "Juan Redondo", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Escuela de Informática — Duoc UC San Joaquín", style = MaterialTheme.typography.bodySmall)

                // Separador
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Desarrollador 2
                Text(text = "W. Mauricio Palominos", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Escuela de Informática — Duoc UC San Joaquín", style = MaterialTheme.typography.bodySmall)

                // Separador
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Desarrollador 3
                Text(text = "Sebastian Cortés", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Escuela de Informática — Duoc UC San Joaquín", style = MaterialTheme.typography.bodySmall)
            }
        }

        // ===== CARD 3: DESCRIPCIÓN =====
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    text = "Esta app fue creada con el objetivo de publicar comics de diversos autores. Combina navegación, diseño y componentes interactivos.",
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
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Contacto",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
                Text("📧 Email: contacto@duocuc.cl", style = MaterialTheme.typography.bodyMedium)
                Text("🌐 Web: www.duoc.cl", style = MaterialTheme.typography.bodyMedium)
                Text("📍 Ubicación: San Joaquín, Santiago - Chile", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // BOTÓN: Ir a Registrarse
        OutlinedButton(
            onClick = {
                // Usamos la ruta "register" definida en AppNavigation.kt
                navController.navigate("register")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f) // Ocupa el 80% del ancho para un buen centrado
                .height(50.dp)
        ) {
            Text("Crear una Cuenta", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTÓN PARA VOLVER AL INICIO
        Button(
            onClick = {
                // Usamos la ruta "inicio" definida en AppNavigation.kt
                navController.navigate("inicio")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f) // Mismo ancho que el botón de arriba para consistencia
                .height(50.dp)
        ) {
            Text("Volver al inicio", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


