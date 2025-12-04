package com.example.miappbasica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miappbasica.data.local.entity.User
import com.example.miappbasica.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Define los diferentes estados posibles de la UI para la pantalla de registro.
 */
data class RegisterUiState(
    val isLoading: Boolean = false,
    val registrationSuccess: Boolean = false,
    val errorMessage: String? = null
)

/**
 * ViewModel para la pantalla de registro y visualización de datos.
 * Contiene la lógica para registrar un nuevo usuario y exponer la lista de todos los usuarios.
 *
 * @param userRepository El repositorio para realizar las operaciones de datos.
 */
class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    // --- ESTADO PARA LA PANTALLA DE REGISTRO ---
    // Flujo de estado privado que solo este ViewModel puede modificar.
    private val _uiState = MutableStateFlow(RegisterUiState())
    // Flujo de estado público e inmutable que la UI de registro observará.
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    // --- DATOS PARA LA PANTALLA DE DATOS (DataScreen) ---
    // ▼▼▼ CÓDIGO AÑADIDO ▼▼▼
    /**
     * Expone un Flow con la lista completa de usuarios desde el repositorio.
     * La UI (DataScreen) se suscribirá a este Flow para mostrar los datos.
     */
    val allUsers: Flow<List<User>> = userRepository.getAllUsers()
    // ▲▲▲ FIN DEL CÓDIGO AÑADIDO ▲▲▲

    /**
     * Inicia el proceso de registro de un nuevo usuario.
     */
    fun registerUser(name: String, email: String, pass: String) {
        // Validaciones de entrada
        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Todos los campos son obligatorios") }
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update { it.copy(errorMessage = "El formato del email no es válido") }
            return
        }

        // Inicia el estado de carga
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                // Comprobar si el email ya existe
                val existingUser = userRepository.findUserByEmail(email)

                if (existingUser == null) {
                    // Si no existe, crear el nuevo usuario.
                    // ¡ADVERTENCIA DE SEGURIDAD! En una app real, la contraseña NUNCA
                    // se guarda en texto plano. Se debe usar una librería de hashing.
                    val newUser = User(name = name, email = email, password = pass)

                    // Insertar el usuario.
                    userRepository.insertUser(newUser)

                    // Emitir el estado de éxito.
                    _uiState.update { it.copy(isLoading = false, registrationSuccess = true) }
                } else {
                    // El email ya existe, emitir un mensaje de error.
                    _uiState.update { it.copy(isLoading = false, errorMessage = "El email ya está registrado") }
                }
            } catch (e: Exception) {
                // Capturar cualquier otro error inesperado.
                _uiState.update { it.copy(isLoading = false, errorMessage = "Error inesperado: ${e.message}") }
            }
        }
    }

    /**
     * Limpia el mensaje de error después de haber sido mostrado en la UI.
     */
    fun consumeErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * Resetea el estado de éxito del registro.
     * Se debe llamar después de haber navegado fuera de la pantalla de registro.
     */
    fun resetRegistrationState() {
        _uiState.update { it.copy(registrationSuccess = false) }
    }
}

