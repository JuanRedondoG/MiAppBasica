package com.example.miappbasica.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miappbasica.ComicApplication
// Limpiamos los imports. Ahora solo se importan las pantallas necesarias.
import com.example.miappbasica.ui.screen.AcercaDeScreen // Import explícito y correcto
import com.example.miappbasica.ui.screen.ColeccionScreen
import com.example.miappbasica.ui.screen.ConfiguracionScreen
import com.example.miappbasica.ui.screen.InicioScreen
import com.example.miappbasica.ui.screen.LoginScreen
import com.example.miappbasica.ui.screen.RegistroScreen
import com.example.miappbasica.ui.view.DatabaseDebugScreen
import com.example.miappbasica.ui.viewmodel.LoginViewModel
import com.example.miappbasica.ui.viewmodel.LoginViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModelFactory = LoginViewModelFactory(
        (LocalContext.current.applicationContext as ComicApplication).database.comicDao()
    )

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- Definición de Rutas (Pantallas) Principales ---
            composable("inicio") { InicioScreen(navController) }
            composable("coleccion") { ColeccionScreen(navController) }
            composable("configuracion") { ConfiguracionScreen(navController) }

            // La llamada a 'AcercaDe' ahora debería resolverse sin problemas.
            composable("acerca") { AcercaDeScreen(navController = navController) }



            // --- Rutas de Autenticación (Login y Registro) ---
            composable("login") {
                val loginViewModel: LoginViewModel = viewModel(factory = viewModelFactory)
                val uiState by loginViewModel.uiState.collectAsState()

                LoginScreen(
                    navController = navController,
                    uiState = uiState,
                    onEmailChange = { loginViewModel.onRegistroChange(email = it) },
                    onPasswordChange = { loginViewModel.onRegistroChange(pass = it) },
                    onLoginClick = { loginViewModel.realizarLogin() },
                    onNavigateToRegister = { navController.navigate("registro") },
                    onNavigateToInicio = { navController.navigate("inicio") { popUpTo("inicio") { inclusive = true } } },
                    onResetEvents = { loginViewModel.resetearEventos() }
                )
            }

            composable("registro") {
                val loginViewModel: LoginViewModel = viewModel(factory = viewModelFactory)
                val uiState by loginViewModel.uiState.collectAsState()

                RegistroScreen(
                    navController = navController,
                    uiState = uiState,
                    onNombreChange = { loginViewModel.onRegistroChange(nombre = it) },
                    onEmailChange = { loginViewModel.onRegistroChange(email = it) },
                    onPasswordChange = { loginViewModel.onRegistroChange(pass = it) },
                    onRegistroClick = { loginViewModel.realizarRegistro() },
                    onNavigateToLogin = { navController.navigate("login") },
                    onResetMessages = { loginViewModel.resetearEventos() }
                )
            }

            // --- Ruta de Depuración ---
            composable("databaseDebug") {
                val loginViewModel: LoginViewModel = viewModel(factory = viewModelFactory)
                DatabaseDebugScreen(
                    viewModel = loginViewModel,
                    navController = navController
                )
            }
        }
    }
}
