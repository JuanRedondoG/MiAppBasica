package com.example.miappbasica.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.miappbasica.data.local.database.AppDatabase
import com.example.miappbasica.data.repository.UserRepository
import com.example.miappbasica.ui.screen.login.LoginViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenUltimate(
    navController: NavHostController,
) {
    // --- INYECCIÓN DE DEPENDENCIAS MANUAL ---
    val context = LocalContext.current.applicationContext
    val db = AppDatabase.getDatabase(context)
    val userRepository = UserRepository(db.userDao())
    val viewModelFactory = LoginViewModelFactory(userRepository)
    val loginViewModel: LoginViewModel = viewModel(factory = viewModelFactory)

    // --- ESTADOS DE LA UI ---
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isForgotPassword by remember { mutableStateOf(false) }

    val uiState by loginViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // --- EFECTOS (LÓGICA) ---

    // ▼▼▼ BLOQUE DE CÓDIGO ACTUALIZADO ▼▼▼
    // DESPUÉS: Navega a "coleccion"
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            // La nueva ruta de destino es "coleccion"
            navController.navigate("coleccion") { // <-- LÍNEA MODIFICADA
                // Limpiamos el historial de navegación hasta "home" para que
                // el usuario no pueda volver a la pantalla de login con el botón de "atrás".
                popUpTo("home") { inclusive = true }
                // Evita crear múltiples instancias de ColeccionScreen si se presiona el botón rápido.
                launchSingleTop = true
            }
            // Reseteamos el estado para evitar re-navegaciones accidentales.
            loginViewModel.resetLoginState()
        }
    }
    // ▲▲▲ FIN DEL BLOQUE ACTUALIZADO ▲▲▲

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            loginViewModel.consumeErrorMessage()
        }
    }

    // --- UI (VISTA) ---
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(if (isForgotPassword) "Recuperar Contraseña" else "Iniciar Sesión") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isForgotPassword) {
                            isForgotPassword = false
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isForgotPassword) "Recupera tu acceso" else "Bienvenido de Nuevo",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isForgotPassword) "Introduce tu email para continuar." else "Inicia sesión para ver tu colección.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = uiState.errorMessage != null
            )

            if (!isForgotPassword) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = uiState.errorMessage != null
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (isForgotPassword) {
                        // TODO: Lógica de recuperación
                    } else {
                        loginViewModel.login(email, password)
                    }
                },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = if (isForgotPassword) "Enviar Instrucciones" else "Iniciar Sesión",
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isForgotPassword) {
                TextButton(onClick = { isForgotPassword = false }) {
                    Text("Volver a Iniciar Sesión")
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { isForgotPassword = true }) {
                        Text("¿Olvidaste tu contraseña?")
                    }
                    TextButton(onClick = { navController.navigate("register") }) {
                        Text("Crear cuenta")
                    }
                }
            }
        }
    }
}
