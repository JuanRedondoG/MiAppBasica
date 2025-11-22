package com.example.miappbasica.navigation
// Paquete donde guardamos la lógica de navegación de la app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.miappbasica.ui.screen.AcercaDeScreen
import com.example.miappbasica.ui.screen.ColeccionScreen
import com.example.miappbasica.ui.screen.ConfiguracionScreen
import com.example.miappbasica.ui.screen.InicioScreen
import com.example.miappbasica.ui.screen.LoginScreen
import com.example.miappbasica.ui.screen.RegisterScreen


// Función principal de navegación de la app
@Composable
fun AppNavigation() {
    // Controlador de navegación → maneja el historial y las rutas activas
    val navController = rememberNavController()
    // Observamos el estado del back stack para saber la ruta actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Lista de rutas donde queremos que la barra de navegación sea visible
    val bottomBarRoutes = setOf("inicio", "coleccion", "configuracion", "acerca")

    // Scaffold es la estructura base de la UI en Compose
    Scaffold(
        bottomBar = {
            // Mostramos la barra solo si la ruta actual está en nuestra lista
            if (currentRoute in bottomBarRoutes) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        // NavHost → contiene todas las rutas (pantallas) de la app
        NavHost(
            navController = navController,       // Controlador de navegación
            startDestination = "inicio",         // Ruta inicial al abrir la app
            modifier = Modifier.padding(innerPadding) // Respeta padding del Scaffold
        ) {
            // --- RUTAS DE LA APP ---

            // Ruta para la pantalla de Login (sin barra de navegación)
            composable("login") { LoginScreen(navController) }
            
            // Ruta para la pantalla de Registro
            composable("register") { RegisterScreen(navController) }

            // Pantalla de Inicio
            composable("inicio") { InicioScreen(navController) }

            // Pantalla de Colección
            composable("coleccion") { ColeccionScreen(navController) }

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