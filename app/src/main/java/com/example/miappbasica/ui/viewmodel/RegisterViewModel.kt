package com.example.miappbasica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miappbasica.data.local.entity.User
import com.example.miappbasica.data.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de registro.
 * Contiene la lógica para validar y registrar un nuevo usuario.
 *
 * @param userRepository El repositorio para realizar las operaciones de datos.
 */
class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Función para ser llamada desde la UI cuando el usuario presiona "Registrar"
    fun registerUser(name: String, email: String, pass: String) {
        // viewModelScope es un CoroutineScope ligado al ciclo de vida del ViewModel.
        // Se cancelará automáticamente cuando el ViewModel sea destruido.
        viewModelScope.launch {

0


            // 1. Validar que los campos no estén vacíos (puedes añadir más validaciones)
            if (name.isNotBlank() && email.isNotBlank() && pass.isNotBlank()) {

                // 2. Comprobar si ya existe un usuario con ese email para evitar duplicados
                val existingUser = userRepository.getUserByEmail(email)

                if (existingUser == null) {
                    // 3. Si no existe, crea el nuevo objeto User
                    // IMPORTANTE: En una app real, la contraseña NUNCA debe guardarse en texto plano.
                    // Deberías usar una librería como BCrypt para "hashear" la contraseña.
                    // Para este ejemplo, la guardaremos directamente.
                    val newUser = User(name = name, email = email, password = pass) // Asegúrate que tu entidad User tiene el campo 'password'

                    // 4. Inserta el nuevo usuario a través del repositorio
                    userRepository.insertUser(newUser)

                    // Aquí podrías emitir un estado de "Registro Exitoso" para que la UI navegue
                } else {
                    // Aquí podrías emitir un estado de "Error: El email ya está en uso"
                }
            } else {
                // Emitir estado de "Error: Todos los campos son requeridos"
            }
        }
    }
}
