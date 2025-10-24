package com.example.miappbasica.ui.screen
// Paquete donde se definen las pantallas de la aplicación

// ===== IMPORTS =====
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource // <-- 1. IMPORTANTE
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.miappbasica.R // <-- 2. IMPORTANTE

// ===== YA NO NECESITAMOS LA DATA CLASS NI LA LISTA AQUÍ =====
// data class Comic(...)
// val misComics = listOf(...)


// ===== COMPOSABLE =====
@Composable
fun ColeccionScreen(navController: NavHostController) {
    // 3. Obtenemos la lista de cómics desde nuestra nueva fuente de datos reactiva
    val misComics = getMisComics()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        // ===== 1. Título de la pantalla =====
        item {
            Text(
                // ANTES: text = "Mi Colección",
                text = stringResource(id = R.string.collection_titulo), // <-- DESPUÉS
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // ===== 2. Lista de Cómics =====
        items(misComics) { comic ->
            ComicCardItem(comic = comic)
        }

        // ===== 3. Botón para volver al inicio =====
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    // .padding(horizontal = 16.dp) // <-- Lo eliminamos, el LazyColumn ya lo tiene
                    .height(50.dp)
            ) {
                // ANTES: Text("Volver al Inicio", fontSize = 16.sp)
                Text(
                    text = stringResource(id = R.string.about_volver_inicio), // <-- DESPUÉS (reutilizamos la string de "About")
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Composable que define cómo se ve cada elemento (cómic) en la lista.
 * ESTE COMPOSABLE NO NECESITA CAMBIOS, ya que recibe los textos traducidos.
 */
@Composable
fun ComicCardItem(comic: Comic) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = comic.titulo,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                // Modificamos ligeramente para que no diga "por por Alan Moore"
                text = comic.autor,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
            )
            Text(
                text = comic.sinopsis,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )
        }
    }
}

