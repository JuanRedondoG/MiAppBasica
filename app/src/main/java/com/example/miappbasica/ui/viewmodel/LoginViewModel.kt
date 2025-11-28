package com.example.miappbasica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miappbasica.data.ComicDao
import com.example.miappbasica.data.LoginEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Define la estructura de datos para el estado de la UI relacionado con el login/registro.
 * Esta es la única "fuente de verdad" que la pantalla de Login conoce y observa.
 */
data class LoginUiState(
    val nombre: String = "",
    val email: String = "",
    val pass: String = "",
    val mensajeError: String? = null,
    val mensajeExito: String? = null,
    val loginExitoso: Boolean = false
)

/**
 * Contiene toda la lógica de negocio para las pantallas de Login, Registro y
 * la visualización de la base de datos.
 */
class LoginViewModel(private val comicDao: ComicDao) : ViewModel() {

    // --- ESTADO PARA LOGIN Y REGISTRO ---
    // _uiState es privado y mutable. Solo el ViewModel puede modificar este estado.
    private val _uiState = MutableStateFlow(LoginUiState())
    // uiState es público e inmutable. Las pantallas solo pueden leerlo y reaccionar a sus cambios.
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // --- ESTADO PARA LA LISTA DE USUARIOS (PANTALLA DE DEBUG) ---
    // _usersList es privado y mutable.
    private val _usersList = MutableStateFlow<List<LoginEntity>>(emptyList())
    // usersList es público para que la pantalla de depuración pueda observar la lista de usuarios.
    val usersList: StateFlow<List<LoginEntity>> = _usersList.asStateFlow()

    /**
     * El bloque init se ejecuta una sola vez cuando el ViewModel es creado.
     * Es el lugar perfecto para iniciar la recolección de datos de un Flow.
     */
    init {
        // Inicia la observación de la base de datos para obtener la lista completa de usuarios.
        // La corrutina se cancelará automáticamente cuando el ViewModel sea destruido.
        viewModelScope.launch {
            comicDao.getAllUsers().collect { listaDeUsuarios ->
                _usersList.value = listaDeUsuarios
            }
        }
    }

    /**
     * Actualiza el estado de los campos del formulario.
     * Se llama desde la UI cada vez que el usuario escribe algo.
     */
    fun onRegistroChange(nombre: String = _uiState.value.nombre, email: String = _uiState.value.email, pass: String = _uiState.value.pass) {
        _uiState.value = _uiState.value.copy(
            nombre = nombre,
            email = email,
            pass = pass,
            mensajeError = null // Limpia errores anteriores al empezar a escribir.
        )
    }

    /**
     * Contiene la lógica para registrar un nuevo usuario.
     */
    fun realizarRegistro() {
        val nombre = _uiState.value.nombre
        val email = _uiState.value.email
        val pass = _uiState.value.pass

        // Validaciones de entrada
        if (nombre.isBlank() || email.isBlank() || pass.isBlank()) {
            _uiState.value = _uiState.value.copy(mensajeError = "Todos los campos son obligatorios.")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = _uiState.value.copy(mensajeError = "El formato del correo no es válido.")
            return
        }

        // Ejecuta la operación de base de datos en una corrutina para no bloquear la UI.
        viewModelScope.launch {
            if (comicDao.getLoginByEmail(email) != null) {
                _uiState.value = _uiState.value.copy(mensajeError = "Este correo electrónico ya está registrado.")
            } else {
                val nuevoUsuario = LoginEntity(nombre = nombre, email = email, password = pass)
                comicDao.insertLogin(nuevoUsuario)
                _uiState.value = _uiState.value.copy(mensajeExito = "¡Registro exitoso! Ya puedes iniciar sesión.")
            }
        }
    }

    /**
     * Contiene la lógica para iniciar sesión con un usuario existente.
     */
    fun realizarLogin() {
        val email = _uiState.value.email
        val pass = _uiState.value.pass

        if (email.isBlank() || pass.isBlank()) {
            _uiState.value = _uiState.value.copy(mensajeError = "Por favor, ingresa email y contraseña.")
            return
        }

        viewModelScope.launch {
            val usuario = comicDao.buscarUsuario(email, pass)
            if (usuario != null) {
                // Lógica de "transacción" movida desde el DAO al ViewModel
                // 1. Desconecta a cualquier otro usuario que pudiera estar logueado.
                comicDao.clearLoggedInStatus()
                // 2. Establece al usuario actual como logueado.
                comicDao.updateUserAsLoggedIn(usuario.id)

                // Éxito: Actualiza el estado para que la UI pueda reaccionar (ej. navegar a otra pantalla).
                _uiState.value = _uiState.value.copy(loginExitoso = true, mensajeError = null)
            } else {
                // Error: Muestra un mensaje de error en la UI.
                _uiState.value = _uiState.value.copy(mensajeError = "Credenciales incorrectas.")
            }
        }
    }

    /**
     * Limpia los "eventos" de un solo uso, como el estado de éxito del login o los mensajes.
     * Se debe llamar desde la UI después de que esta haya reaccionado (ej. después de navegar o mostrar un Toast).
     */
    fun resetearEventos() {
        _uiState.value = _uiState.value.copy(
            loginExitoso = false,
            mensajeError = null,
            mensajeExito = null
        )
    }
}
