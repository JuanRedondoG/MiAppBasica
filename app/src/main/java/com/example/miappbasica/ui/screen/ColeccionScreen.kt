package com.example.miappbasica.ui.screen
// Paquete donde se definen las pantallas de la aplicación

// ===== IMPORTS =====
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// ... (data class Comic y la lista misComics permanecen igual) ...
data class Comic(
    val id: Int,
    val titulo: String,
    val autor: String,
    val sinopsis: String
)

val misComics = listOf(
    Comic(1, "Watchmen", "Alan Moore", "Una deconstrucción del género de superhéroes..."),
    Comic(2, "The Dark Knight Returns", "Frank Miller", "Un Batman envejecido vuelve de su retiro..."),
    Comic(3, "Maus", "Art Spiegelman", "Una novela gráfica que narra la experiencia del padre del autor..."),
    Comic(4, "Saga, Vol. 1", "Brian K. Vaughan", "Dos soldados de razas extraterrestres en guerra..."),
    Comic(5, "The Walking Dead", "Robert Kirkman", "Tras un apocalipsis zombi, un grupo de supervivientes...")
)


// ===== COMPOSABLE =====
@Composable
fun ColeccionScreen(navController: NavHostController) {
    // utilizamos un LazyColumn para desplazar la aplicacion.
    // asi todos los elementos se podran desplazar unidos.
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp) // Padding lateral para todos los elementos
    ) {
        // ===== 1. Título de la pantalla =====
        item {
            Text(
                text = "Mi Colección",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // ===== 2. Lista de Cómics =====
        // Creamos una Card por cada cómic en la lista "misComics"
        items(misComics) { comic ->
            // Le quitamos el padding horizontal al ComicCardItem porque ya lo tiene el LazyColumn
            ComicCardItem(comic = comic)
        }

        // ===== 3. Botón para volver al inicio =====
        item {
            // Espacio entre la lista y el botón
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.popBackStack() //accion para volver atras
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp)
            ) {
                // Estilo de texto idéntico al botón de InicioScreen
                Text("Volver al Inicio", fontSize = 16.sp)
            }

            // Espacio final para que el botón no quede pegado al borde inferior
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Composable que define cómo se ve cada elemento (cómic) en la lista.
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
                text = "por ${comic.autor}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            Text(
                text = comic.sinopsis,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )
        }
    }
}

