package com.example.miappbasica
// Paquete principal de la app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.miappbasica.ui.theme.LocalThemeState
import com.example.miappbasica.ui.theme.ThemeState
import com.example.miappbasica.navigation.AppNavigation
import com.example.miappbasica.ui.theme.MiAppBasicaTheme


// ===== ACTIVIDAD PRINCIPAL =====
// MainActivity es el punto de entrada de la aplicación Android
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Obtenemos el valor booleano actual de nuestro estado global
            val isDarkTheme by ThemeState.isDarkTheme

            // Proveemos el estado del tema a toda la app
            CompositionLocalProvider(LocalThemeState provides ThemeState) {
                MiAppBasicaTheme(
                    darkTheme = isDarkTheme // Pasamos el valor a nuestro tema
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}
/*Explicación rápida:

MainActivity → Es la actividad principal, el punto de entrada de tu app Android.

setContent { ... } → Reemplaza los tradicionales XML layouts y define la interfaz con Compose.

MiAppBasicaTheme → Aplica los colores, tipografía y estilos globales definidos en tu tema.

AppNavigation() → Carga el sistema de navegación de tu app (el NavHost y la BottomNavBar).*/