// En: com/example/miappbasica/idiomas/LocaleHelper.kt
package com.example.miappbasica.idiomas

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale


object LocaleHelper {
    fun setLocale(context: Context, languageCode: String) { // <-- Añadido el parámetro context
        val localeList = LocaleListCompat.forLanguageTags(languageCode)
        // La llamada a setApplicationLocales es estática, no necesita el context directamente,
        // pero tenerlo aquí nos asegura que la llamada se hace desde un lugar válido de la app.
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    fun getCurrentLocaleCode(): String {
        // Esta función se mantiene igual que antes
        val locales = AppCompatDelegate.getApplicationLocales()
        return if (!locales.isEmpty) {
            locales[0]?.toLanguageTag() ?: Locale.getDefault().toLanguageTag()
        } else {
            Locale.getDefault().toLanguageTag()
        }
    }
}


