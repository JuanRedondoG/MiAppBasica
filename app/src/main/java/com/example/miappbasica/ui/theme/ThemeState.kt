package com.example.miappbasica.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

/**
 * Objeto que contiene el estado global que determina si el modo oscuro está activo.
 * Usamos mutableStateOf para que Compose recomponga la UI cuando su valor cambie.
 */
object ThemeState {
    val isDarkTheme = mutableStateOf(false) // Por defecto, el tema es claro
}

/**
 * CompositionLocal que usaremos para pasar el estado del tema
 * a cualquier composable que lo necesite, como ConfiguracionScreen.
 */
val LocalThemeState = compositionLocalOf { ThemeState }
    