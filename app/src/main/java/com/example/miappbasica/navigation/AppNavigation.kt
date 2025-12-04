package com.example.miappbasica.navigation
// Paquete donde guardamos la lógica de navegación de la app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.miappbasica.ui.screen.*
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
            // Hacemos una comprobación más robusta para que funcione con argumentos
            if (bottomBarRoutes.any { currentRoute?.startsWith(it) == true }) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        // NavHost → contiene todas las rutas (pantallas) de la app
        NavHost(
            navController = navController,       // Controlador de navegación
            // La ruta de inicio correcta, sin argumentos
            startDestination = "inicio",
            modifier = Modifier.padding(innerPadding) // Respeta padding del Scaffold
        ) {
            // --- RUTAS DE LA APP ---

            // Ruta para la pantalla de Login (sin barra de navegación)
            //composable("login") { LoginScreen(navController) }

            // --- AÑADE ESTA NUEVA RUTA PARA TU PANTALLA ULTIMATE ---
            composable("login_ultimate") {
                LoginScreenUltimate(navController = navController)
            }





            // Ruta para la pantalla de Registro
            composable("register") { RegisterScreen(navController) }

            // Ruta para la pantalla de DataScreen
            composable("data_screen") { DataScreen(navController) }

            // Pantalla de Inicio (ahora con un argumento opcional para el username)
            composable(
                route = "inicio?username={username}",
                arguments = listOf(navArgument("username") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null // Aseguramos que el valor por defecto es null
                })
            ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username")
                InicioScreen(navController = navController, username = username)
            }

            // Pantalla de Colección
            composable("coleccion") { ColeccionScreen(navController) }

            // Pantalla de Configuración
            composable("configuracion") { ConfiguracionScreen(navController) }

            // Pantalla de Acerca de
            composable("acerca") { AcercaDeScreen(navController) }

        }
    }
}