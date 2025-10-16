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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * Estructura de datos para representar un cómic.
 * Puedes mover este "data class" a su propio archivo en un futuro,
 * por ejemplo, en un paquete llamado "model".
 */
data class Comic(
    val id: Int,
    val titulo: String,
    val autor: String,
    val sinopsis: String
)
/**
 Esta parte de aca sirve para agregar la lista de los comiccs pero no puedo probarla para ver si quedo bien
*/
val misComics = listOf(

    Comic(1, "Watchmen", "Alan Moore", "Una deconstrucción del género de superhéroes en un mundo al borde de la guerra nuclear."),
    Comic(2, "The Dark Knight Returns", "Frank Miller", "Un Batman envejecido vuelve de su retiro para luchar contra una nueva ola de criminalidad en Gotham."),
    Comic(3, "Maus", "Art Spiegelman", "Una novela gráfica que narra la experiencia del padre del autor como un judío polaco y superviviente del Holocausto."),
    Comic(4, "Saga, Vol. 1", "Brian K. Vaughan", "Dos soldados de razas extraterrestres en guerra se enamoran y luchan por proteger a su hija recién nacida."),
    Comic(5, "The Walking Dead", "Robert Kirkman","dadadada")
)

// ===== COMPOSABLE =====
@Composable
fun ColeccionScreen(navController: NavHostController) {
    // LazyColumn es la mejor opción para mostrar listas, ya que es eficiente.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp), // Padding solo a los lados para que las cards ocupen todo el ancho
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        item {
            Text(
                text = "Mi Colección de Cómics",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }

        // Creamos una Card por cada cómic en la lista "misComics"
        items(misComics) { comic ->
            ComicCardItem(comic = comic)
        }

        // Espacio al final de la lista
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Composable que define cómo se ve cada elemento (cómic) en la lista.
 * Usa un estilo de Card similar al que tenías en PerfilScreen.
 */
@Composable
fun ComicCardItem(comic: Comic) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Espacio entre cada Card
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Título del Cómic
            Text(
                text = comic.titulo,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            // Autor del Cómic
            Text(
                text = "por ${comic.autor}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Separador, igual al que usabas en PerfilScreen
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Sinopsis del Cómic
            Text(
                text = comic.sinopsis,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3 // Limita el texto a 3 líneas para no ocupar tanto espacio
            )
        }
    }
}
