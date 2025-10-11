package com.example.miappbasica.ui.screen

// ===== IMPORTS =====
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.miappbasica.R // Asegúrate de tener imágenes en res/drawable

// ===== COMPOSABLE PRINCIPAL =====
@Composable
fun InicioScreen(navController: NavHostController) {
    // Añadimos un scroll vertical para que quepa todo el contenido
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Permite desplazar toda la pantalla
            .padding(bottom = 16.dp)
    ) {

        // ===== BANNER DE NOVEDADES (ERA TU BANNER DECORATIVO) =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { /* Navegar al cómic destacado */ }, // Hazlo interactivo
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.comic_banner_placeholder), // Usa una imagen atractiva aquí
                contentDescription = "Nuevo lanzamiento",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Asegura que la imagen cubra el espacio
            )
            // Título superpuesto sobre el banner
            Text(
                text = "¡NUEVO! Las Aventuras de Astro",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== SECCIÓN: CONTINUAR LEYENDO =====
        SeccionComics(
            titulo = "Continuar Leyendo",
            comics = listOf(R.drawable.comic_cover_1, R.drawable.comic_cover_2) // Lista de portadas
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== SECCIÓN: LOS MÁS POPULARES =====
        SeccionComics(
            titulo = "Los más Populares",
            comics = listOf(R.drawable.comic_cover_3, R.drawable.comic_cover_4, R.drawable.comic_cover_1)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== BOTÓN PARA IR A "MI COLECCIÓN" =====
        Button(
            onClick = { navController.navigate("coleccion") }, // Navega a la colección del usuario
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
        ) {
            Text("Ver Mi Colección Completa", fontSize = 16.sp)
        }
    }
}

// ===== COMPOSABLE REUTILIZABLE PARA LAS "ESTANTERÍAS" DE CÓMICS =====
@Composable
fun SeccionComics(titulo: String, comics: List<Int>) {
    Column(modifier = Modifier.padding(start = 16.dp)) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        // Fila horizontal que se puede desplazar (scroll)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(comics) { comicCoverResId ->
                ComicCard(imageResId = comicCoverResId)
            }
        }
    }
}


// ===== COMPOSABLE PARA LA TARJETA DE UN CÓMIC INDIVIDUAL =====
@Composable
fun ComicCard(imageResId: Int) {
    Card(
        modifier = Modifier
            .width(140.dp) // Ancho fijo para las portadas
            .clickable { /* Navegar a los detalles de este cómic */ },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Portada de cómic",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), // Altura fija
            contentScale = ContentScale.Crop // La imagen se ajusta y recorta
        )
    }
}
