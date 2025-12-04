package com.example.miappbasica.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.example.miappbasica.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// El estado de la UI que la vista observará
data class LoginUiState(
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val errorMessage: String? = null,
    val loggedInUsername: String? = null
)

class LoginViewModel(
    private val userRepository: UserRepository // Inyecta el repositorio
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        // Validaciones básicas de entrada
        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email y contraseña no pueden estar vacíos") }
            return
        }

        // Inicia el estado de carga
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                // 1. Buscamos al usuario en la base de datos a través del repositorio
                val user = userRepository.findUserByEmail(email)

                // 2. Comprobamos si el usuario existe y la contraseña es correcta
                if (user != null && checkPassword(password, user.password)) {
                    // Éxito: actualizamos el estado para navegar
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            loggedInUsername = user.name
                        )
                    }
                } else {
                    // Error: Credenciales incorrectas
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Email o contraseña incorrectos"
                        )
                    }
                }
            } catch (e: Exception) {
                // Error genérico (ej. fallo de la base de datos)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Ocurrió un error: ${e.message}"
                    )
                }
            }
        }
    }

    // Función para limpiar el mensaje de error una vez mostrado en la UI
    fun consumeErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // Función de ejemplo para verificar el hash de la contraseña.
    // En un proyecto real, deberías usar una librería de criptografía como BCrypt.
    private fun checkPassword(password: String, storedHash: String): Boolean {
        // ESTO ES SOLO UN EJEMPLO. NO USAR EN PRODUCCIÓN.
        // Aquí iría la lógica para comparar el hash de la contraseña introducida
        // con el hash almacenado.
        return password == storedHash // ¡REEMPLAZA ESTO!
    }

    /**
     * Resetea el estado de éxito del login.
     * Se debe llamar después de que la navegación se haya completado
     * para prevenir una re-navegación si la UI se recompone.
     */
    fun resetLoginState() {
        _uiState.update { it.copy(loginSuccess = false) }
    }

}


