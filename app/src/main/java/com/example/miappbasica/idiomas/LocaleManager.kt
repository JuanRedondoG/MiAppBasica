// En: com/example/miappbasica/idiomas/LocaleManager.kt
package com.example.miappbasica.idiomas

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

// Este objeto gestionará el estado reactivo del idioma para toda la app.
object LocaleManager {

    // 1. Un StateFlow privado para guardar el código de idioma actual (ej. "es", "en")
    private val _languageCode = MutableStateFlow(getCurrentLanguageCode())

    // 2. Un StateFlow público para que la UI pueda suscribirse a los cambios.
    val languageCode = _languageCode.asStateFlow()

    // 3. Una función para notificar al manager que el idioma ha cambiado.
    fun updateLanguageCode() {
        _languageCode.value = getCurrentLanguageCode()
    }

    // 4. Función privada para obtener el idioma actual de forma consistente.
    private fun getCurrentLanguageCode(): String {
        val tag = LocaleHelper.getCurrentLocaleCode() // Reutilizamos la lógica que ya teníamos
        return tag.split("-").first()
    }
}
