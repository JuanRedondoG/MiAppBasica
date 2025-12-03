package com.example.miappbasica.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.miappbasica.data.local.database.AppDatabase
import com.example.miappbasica.data.repository.UserRepository
import com.example.miappbasica.ui.viewmodel.RegisterViewModel
import com.example.miappbasica.ui.viewmodel.RegisterViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(navController: NavHostController) {

    // --- CONFIGURACIÓN DEL VIEWMODEL ---
    // Reutilizamos la misma configuración para acceder a los datos.
    val context = LocalContext.current
    val db = remember(context) { AppDatabase.getDatabase(context) }
    val userRepository = remember(db) { UserRepository(db.userDao()) }
    val viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(userRepository)
    )

    // --- OBTENCIÓN DE DATOS ---
    // Usamos .collectAsState() para que la UI se actualice automáticamente
    // cuando los datos en la base de datos cambien.
    val users by viewModel.allUsers.collectAsState(initial = emptyList())

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
        // Usamos LazyColumn para un rendimiento óptimo con listas largas.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Comprobamos si la lista de usuarios está vacía.
            if (users.isEmpty()) {
                item {
                    Text("No hay usuarios registrados en la base de datos.")
                }
            } else {
                // Creamos un item por cada usuario en la lista.
                items(users) { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "ID: ${user.id}",
                                style = MaterialTheme.typography.bodySmall
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
            }
        }
    }
}


