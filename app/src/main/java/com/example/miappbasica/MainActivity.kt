package com.example.miappbasica
// Paquete principal de la app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.miappbasica.navigation.AppNavigation // Importamos la navegación de la app
import com.example.miappbasica.ui.theme.MiAppBasicaTheme // Importamos el tema visual de la app

// ===== ACTIVIDAD PRINCIPAL =====
// MainActivity es el punto de entrada de la aplicación Android
class MainActivity : ComponentActivity() {

    // Método que se ejecuta cuando la actividad se crea
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent → establece el contenido de la UI usando Jetpack Compose
        setContent {
            // Aplicamos el tema personalizado de la app
            MiAppBasicaTheme {
                // 🚀 Llamamos a la función que maneja toda la navegación
                AppNavigation()
            }
        }
    }
}
/*Explicación rápida:

MainActivity → Es la actividad principal, el punto de entrada de tu app Android.

setContent { ... } → Reemplaza los tradicionales XML layouts y define la interfaz con Compose.

MiAppBasicaTheme → Aplica los colores, tipografía y estilos globales definidos en tu tema.

AppNavigation() → Carga el sistema de navegación de tu app (el NavHost y la BottomNavBar).*/