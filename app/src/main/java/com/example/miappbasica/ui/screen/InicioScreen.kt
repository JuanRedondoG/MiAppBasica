package com.example.miappbasica.ui.screen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.miappbasica.R

// ===== COMPOSABLE PRINCIPAL =====
@Composable
fun InicioScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {

        // ===== BANNER DE NOVEDADES =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { /* Podrías navegar al cómic destacado */ },
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.comic_banner_placeholder),
                contentDescription = stringResource(id = R.string.home_nuevo),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.home_nuevo),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== BOTÓN PARA INICIAR SESIÓN (CORREGIDO) =====
        Button(
            // 👇 LA CORRECCIÓN CLAVE ESTÁ AQUÍ 👇
            // En lugar de mostrar un diálogo, navegamos a la pantalla de login.
            // AppNavigation se encargará de mostrar LoginScreen con su ViewModel.
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Iniciar Sesión", // O puedes usar un string resource
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== SECCIÓN: CONTINUAR LEYENDO =====
        SeccionComics(
            titulo = stringResource(id = R.string.home_continuar_leyendo),
            comics = listOf(R.drawable.comic_cover_1, R.drawable.comic_cover_2)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== SECCIÓN: LOS MÁS POPULARES =====
        SeccionComics(
            titulo = stringResource(id = R.string.home_mas_populares),
            comics = listOf(R.drawable.comic_cover_3, R.drawable.comic_cover_4, R.drawable.comic_cover_1)
        )
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
            .width(140.dp)
            .clickable { /* Navegar a los detalles de este cómic */ },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Portada de cómic",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}
