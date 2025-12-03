package com.example.miappbasica.ui.screen

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.miappbasica.data.local.database.AppDatabase
import com.example.miappbasica.data.repository.UserRepository
import com.example.miappbasica.ui.viewmodel.RegisterViewModel
import com.example.miappbasica.ui.viewmodel.RegisterViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// CORRECCIÓN 1: La firma de la función se limpia. Solo necesita el NavController.
fun RegisterScreen(navController: NavHostController) {

    // --- ESTADOS DE LA UI ---
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // --- CONFIGURACIÓN DEL VIEWMODEL (ÚNICA Y CORRECTA) ---
    // No se toca, esto ya estaba bien.
    val context = LocalContext.current
    val db = remember(context) { AppDatabase.getDatabase(context) }
    val userRepository = remember(db) { UserRepository(db.userDao()) }
    val viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(userRepository)
    )

    // --- INTERFAZ DE USUARIO ---
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Cuenta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()), // Permite scroll en pantallas pequeñas
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Únete a la Aventura",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // CAMPO AÑADIDO: Nombre de Usuario
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de Usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo Confirmar Contraseña
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            // Muestra el mensaje de error si existe
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de Registro
            Button(
                onClick = {
                    // CORRECCIÓN 2: La lógica se ejecuta SÓLO al hacer clic en el botón.
                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        errorMessage = "Todos los campos son obligatorios."
                    } else if (password != confirmPassword) {
                        errorMessage = "Las contraseñas no coinciden."
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "El formato del email no es válido."
                    } else {
                        errorMessage = null
                        // Llamamos al ViewModel para que haga el trabajo
                        viewModel.registerUser(name, email, password)

                        // Navegamos a la pantalla de login después del registro exitoso
                        navController.navigate("login") {
                            // Limpiamos el historial para que el usuario no pueda volver a esta pantalla
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Registrarse", fontSize = 16.sp)
            }
        }
    }
}
