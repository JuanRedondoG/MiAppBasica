package com.example.miappbasica.navigation
// Paquete donde guardamos la lógica de navegación de la app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miappbasica.ui.screen.AcercaDeScreen
import com.example.miappbasica.ui.screen.ConfiguracionScreen
import com.example.miappbasica.ui.screen.InicioScreen
import com.example.miappbasica.ui.screen.PerfilScreen

// Función principal de navegación de la app
@Composable
fun AppNavigation() {
    // Controlador de navegación → maneja el historial y las rutas activas
    val navController = rememberNavController()

    // Scaffold es la estructura base de la UI en Compose
    // Aquí lo usamos para colocar la barra de navegación inferior en todas las pantallas
    Scaffold(
        bottomBar = { BottomNavBar(navController) } // Barra inferior personalizada
    ) { innerPadding ->
        // NavHost → contiene todas las rutas (pantallas) de la app
        NavHost(
            navController = navController,       // Controlador de navegación
            startDestination = "inicio",         // Ruta inicial al abrir la app
            modifier = Modifier.padding(innerPadding) // Respeta padding del Scaffold
        ) {
            // Pantalla de Inicio
            composable("inicio") { InicioScreen(navController) }

            // Pantalla de Perfil
            composable("perfil") { PerfilScreen(navController) }

            // Pantalla de Configuración
            composable("configuracion") { ConfiguracionScreen(navController) }

            // Pantalla de Acerca de
            composable("acerca") { AcercaDeScreen(navController) }
        }
    }
}
/*
Explicación general:

rememberNavController() → crea el controlador que maneja la navegación.

Scaffold → estructura base de la app (permite barra inferior, superior, FAB, etc.).

BottomNavBar(navController) → muestra la barra inferior con los ítems definidos.

NavHost → define las rutas y qué pantalla se debe mostrar en cada ruta.

composable("ruta") → define cada pantalla y recibe el navController para navegar.
 */