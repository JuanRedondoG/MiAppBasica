package com.example.miappbasica.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.miappbasica.data.local.database.AppDatabase

// Importaciones necesarias y organizadas

import com.example.miappbasica.data.local.entity.User
import com.example.miappbasica.data.repository.UserRepository
import com.example.miappbasica.ui.viewmodel.RegisterViewModel
import com.example.miappbasica.ui.viewmodel.RegisterViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(navController: NavHostController) {

    // --- CONFIGURACIÓN DEL VIEWMODEL (CORREGIDA Y COMPLETA) ---
    // 1. Obtenemos el contexto para acceder a la base de datos.
    val context = LocalContext.current.applicationContext

    // 2. Creamos la jerarquía de dependencias: Database -> DAO -> Repository.
    val db = AppDatabase.getDatabase(context)
    val userRepository = UserRepository(db.userDao())

    // 3. Usamos la Factory correcta para poder inyectar el repositorio.
    val factory = RegisterViewModelFactory(userRepository)

    // 4. Creamos una instancia de RegisterViewModel, que es el que contiene 'allUsers'.
    val viewModel: RegisterViewModel = viewModel(factory = factory)

    // --- OBTENCIÓN DE DATOS DEL VIEWMODEL ---
    // Recolectamos el Flow de usuarios. La UI se actualizará automáticamente.
    // Esta línea ahora funciona porque 'viewModel' es del tipo correcto (RegisterViewModel).
    val users by viewModel.allUsers.collectAsState(initial = emptyList<User>())

    // --- INTERFAZ DE USUARIO ---
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Base de Datos - Usuarios") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp) // Espacio entre items
        ) {
            if (users.isEmpty()) {
                item {
                    // Mensaje centrado para cuando no hay datos
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay usuarios registrados en la base de datos.",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                // Creamos un Card por cada usuario en la lista
                items(
                    items = users,
                    key = { user -> user.id } // Clave única para optimizar la recomposición
                ) { user ->
                    UserCard(user = user)
                }
            }
        }
    }
}

/**
 * Un Composable reutilizable para mostrar la información de un solo usuario.
 */
@Composable
private fun UserCard(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "ID: ${user.id}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

