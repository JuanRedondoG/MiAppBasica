package com.example.miappbasica.navigation
// Paquete donde se define la barra de navegación inferior de la app

// ===== IMPORTS =====import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
// Íconos predeterminados de Material Design

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
// Componentes de Material 3 para construir la barra inferior

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
// Para definir funciones composables y observar estados

import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
// Para controlar la navegación y saber en qué pantalla estamos

import androidx.compose.ui.graphics.vector.ImageVector
// Tipo de dato que representa íconos vectoriales
import com.example.miappbasica.R
import androidx.compose.ui.res.stringResource

// ===== COMPOSABLE =====
@Composable
fun BottomNavBar(navController: NavHostController) {
    // Lista de ítems que aparecerán en la barra inferior
    val items = listOf(
        NavItem(R.string.navbar_inicio, "inicio", Icons.Filled.Home),
        NavItem(R.string.navbar_coleccion, "coleccion", Icons.Filled.Person),
        NavItem(R.string.navbar_config, "configuracion", Icons.Filled.Settings),
        NavItem(R.string.navbar_acerca, "acerca", Icons.Filled.Info)
    )

    // Componente que dibuja la barra inferior
    NavigationBar {
        // Obtenemos la ruta actual (pantalla activa)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // Recorremos todos los ítems de la barra y los pintamos
        items.forEach { item ->
            // ===== PASO 1: OBTENER EL TEXTO TRADUCIDO =====
            // Usamos el ID del recurso para obtener el string correcto según el idioma.
            val title = stringResource(id = item.titleResId)

            NavigationBarItem(
                // ===== PASO 2: USAR EL TEXTO TRADUCIDO =====
                // Ícono del ítem. Ahora 'contentDescription' usa el texto traducido.
                icon = { Icon(item.icon, contentDescription = title) },

                // Texto debajo del ícono. Ahora 'label' usa el texto traducido.
                label = { Text(text = title) },

                // Se marca como seleccionado si coincide con la ruta actual
                selected = currentRoute == item.route,

                // Acción cuando se hace clic
                onClick = {
                    navController.navigate(item.route) {
                        // Limpia la pila hasta el inicio, pero guarda el estado
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        // Evita duplicar pantallas si ya está activa
                        launchSingleTop = true
                        // Restaura el estado previo (scroll, posición, etc.)
                        restoreState = true
                    }
                }
            )
        }
    }
}

// ===== DATA CLASS =====
// Representa cada ítem de la barra inferior
// - titleResId → ID del recurso de string (ej. R.string.navbar_inicio)
// - route      → nombre de la ruta de navegación
// - icon       → ícono que acompaña el texto
data class NavItem(val titleResId: Int, val route: String, val icon: ImageVector)
/*
Explicación general:

NavigationBar → contenedor de la barra inferior.

NavigationBarItem → cada botón dentro de la barra.

currentBackStackEntryAsState() → permite saber qué pantalla está activa y resaltarla.

NavItem → clase simple que guarda los datos de cada opción de la barra.

*/
