package com.example.miappbasica.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isForgotPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isForgotPassword) "Recuperar Contraseña" else "Iniciar Sesión") },
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
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(!isForgotPassword) {
                Box(
                    modifier = Modifier.size(120.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Icono de Login",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (isForgotPassword) {
                Text("Introduce tu email para recuperar el acceso.")
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; errorMessage = null },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = errorMessage != null
            )

            AnimatedVisibility(!isForgotPassword) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; errorMessage = null },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        isError = errorMessage != null
                    )
                }
            }

            AnimatedVisibility(errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isForgotPassword) {
                Button(
                    onClick = { /* Lógica de envío */ },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Enviar Instrucciones", fontSize = 16.sp)
                }
            } else {
                Button(
                    onClick = {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            if (email == "usuario@comicapp.com" && password == "123456") {
                                // Extraemos el nombre de usuario del email
                                val username = email.substringBefore("@")
                                // Navegamos a la pantalla de inicio, pasando el nombre de usuario
                                navController.navigate("inicio?username=$username") {
                                    // Limpiamos el historial para que no se pueda volver al login
                                    popUpTo(0)
                                }
                            } else {
                                errorMessage = "Email o contraseña incorrectos."
                            }
                        } else {
                            errorMessage = "Por favor, rellena todos los campos."
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Iniciar Sesión", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isForgotPassword) {
                TextButton(onClick = { isForgotPassword = false }) {
                    Text("Volver a Iniciar Sesión")
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(onClick = { navController.navigate("register") }) {
                        Text("¿No tienes una cuenta? Regístrate")
                    }
                    TextButton(onClick = { isForgotPassword = true }) {
                        Text("¿Olvidaste tu contraseña?")
                    }
                }
            }
        }
    }
}
