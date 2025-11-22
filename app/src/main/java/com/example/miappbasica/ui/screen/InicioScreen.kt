package com.example.miappbasica.ui.screen

// ===== IMPORTS =====
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource // <-- 1. ASEGÚRATE DE IMPORTAR ESTO
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.miappbasica.R

// ===== COMPOSABLE PRINCIPAL =====
@Composable
fun InicioScreen(navController: NavHostController) {
    // ===== MANEJO DE LA CÁMARA Y PERMISOS =====
    val context = LocalContext.current
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // Aquí puedes manejar el bitmap de la foto tomada (por ahora no hacemos nada).
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso concedido, lanzar la cámara.
            cameraLauncher.launch(null)
        } else {
            // Permiso denegado. Idealmente, aquí mostrarías un mensaje al usuario.
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        // ===== Contenedor para botones superiores =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            IconButton(
                onClick = {
                    // Comprobar si el permiso ya está concedido
                    when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
                        PackageManager.PERMISSION_GRANTED -> {
                            // Si ya tenemos permiso, lanzamos la cámara
                            cameraLauncher.launch(null)
                        }
                        else -> {
                            // Si no, pedimos el permiso
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.QrCodeScanner,
                    contentDescription = "Escanear QR",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text("Login")
            }
        }

        // ===== BANNER DE NOVEDADES =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { /* Navegar al cómic destacado */ },
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.comic_banner_placeholder),
                contentDescription = stringResource(id = R.string.home_nuevo), // DESPUÉS: Descripción accesible
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                // ANTES: text = "¡NUEVO! Las Aventuras de Astro",
                text = stringResource(id = R.string.home_nuevo), // <-- DESPUÉS
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== SECCIÓN: CONTINUAR LEYENDO =====
        SeccionComics(
            // ANTES: titulo = "Continuar Leyendo",
            titulo = stringResource(id = R.string.home_continuar_leyendo), // <-- DESPUÉS
            comics = listOf(R.drawable.comic_cover_1, R.drawable.comic_cover_2)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== SECCIÓN: LOS MÁS POPULARES =====
        SeccionComics(
            // ANTES: titulo = "Los más Populares",
            titulo = stringResource(id = R.string.home_mas_populares), // <-- DESPUÉS
            comics = listOf(R.drawable.comic_cover_3, R.drawable.comic_cover_4, R.drawable.comic_cover_1)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== BOTÓN PARA IR A "MI COLECCIÓN" =====
        Button(
            onClick = { navController.navigate("coleccion") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
        ) {
            Text(
                // ANTES: text = "Ver Mi Colección Completa",
                text = stringResource(id = R.string.home_ver_coleccion), // <-- DESPUÉS
                fontSize = 16.sp
            )
        }
    }
}

// ===== COMPOSABLE REUTILIZABLE PARA LAS "ESTANTERÍAS" DE CÓMICS =====
@Composable
fun SeccionComics(titulo: String, comics: List<Int>) {
    Column(modifier = Modifier.padding(start = 16.dp)) {
        Text(
            text = titulo, // ANTES: text = titulo,
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
            // La descripción del contenido de las portadas puede ser genérica
            // o podrías pasarla como parámetro si cada cómic tuviera un nombre.
            contentDescription = "Portada de cómic", // Por ahora, se puede quedar así.
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}
