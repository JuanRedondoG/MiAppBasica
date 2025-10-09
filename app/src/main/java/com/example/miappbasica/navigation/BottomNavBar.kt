package com.example.miappbasica.navigation
// Paquete donde se define la barra de navegación inferior de la app

// ===== IMPORTS =====
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

// ===== COMPOSABLE =====
@Composable
fun BottomNavBar(navController: NavHostController) {
    // Lista de ítems que aparecerán en la barra inferior
    val items = listOf(
        NavItem("Inicio", "inicio", Icons.Filled.Home),
        NavItem("Perfil", "perfil", Icons.Filled.Person),
        NavItem("Config", "configuracion", Icons.Filled.Settings),
        NavItem("Acerca", "acerca", Icons.Filled.Info)
    )

    // Componente que dibuja la barra inferior
    NavigationBar {
        // Obtenemos la ruta actual (pantalla activa)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // Recorremos todos los ítems de la barra y los pintamos
        items.forEach { item ->
            NavigationBarItem(
                // Ícono del ítem
                icon = { Icon(item.icon, contentDescription = item.title) },

                // Texto debajo del ícono
                label = { Text(text = item.title) },

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
// - title → texto mostrado
// - route → nombre de la ruta de navegación
// - icon  → ícono que acompaña el texto
data class NavItem(val title: String, val route: String, val icon: ImageVector)

/*
Explicación general:

NavigationBar → contenedor de la barra inferior.

NavigationBarItem → cada botón dentro de la barra.

currentBackStackEntryAsState() → permite saber qué pantalla está activa y resaltarla.

NavItem → clase simple que guarda los datos de cada opción de la barra.

*/