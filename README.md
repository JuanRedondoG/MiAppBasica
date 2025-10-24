# Aplicación de Cómics - Proyecto Demo con Jetpack Compose

Este es un proyecto de aplicación para Android desarrollado como una demostración de los fundamentos del desarrollo móvil moderno utilizando **Jetpack Compose**. La aplicación simula una biblioteca de cómics, permitiendo al usuario navegar por diferentes secciones, ver su colección y configurar preferencias.

El propósito principal de este repositorio es servir como material de aprendizaje y consulta sobre la implementación de características clave en Android.

## Características Principales

-   **UI 100% Declarativa con Jetpack Compose**: Toda la interfaz de usuario está construida con el moderno toolkit de Android, sin usar XML.
-   **Navegación Robusta**: Implementación de `NavHost` y `BottomNavBar` para una navegación fluida entre las diferentes pantallas (Inicio, Colección, Configuración, Acerca de).
-   **Sistema de Temas Dinámico**:
    -   Soporte para **Tema Claro y Oscuro** que se adapta a la configuración del sistema.
    -   Un interruptor en la pantalla de configuración permite al usuario forzar el modo claro/oscuro, y la selección persiste en toda la aplicación.
-   **Internacionalización (i18n)**:
    -   Soporte completo para múltiples idiomas (Español e Inglés).
    -   El usuario puede cambiar el idioma en tiempo real desde la pantalla de configuración, y la aplicación se actualiza **instantáneamente y sin parpadeos**.
-   **Arquitectura Limpia (MVVM Básico)**:
    -   Separación de responsabilidades: UI (`Composables`), Estado (`StateFlow`), Lógica (`ViewModel` / `Manager`) y Datos (`DataSource`).
    -   Uso de `StateFlow` para gestionar y observar el estado de forma reactiva.
-   **Diseño con Material 3**: Implementación de los componentes y guías de diseño de Material Design 3.

## Tecnologías y Librerías Utilizadas

-   **Lenguaje**: [Kotlin](https://kotlinlang.org/)
-   **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
-   **Arquitectura**: Patrón MVVM (Model-View-ViewModel) básico, `StateFlow`.
-   **Navegación**: [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
-   **Internacionalización**: `AppCompatDelegate` para el cambio de idioma y `stringResource` para la carga de textos.
-   **Dependencias Clave**:
    -   `androidx.activity:activity-compose`
    -   `androidx.compose.material3`
    -   `androidx.navigation:navigation-compose`
    -   `androidx.lifecycle:lifecycle-runtime-ktx`
    -   `androidx.appcompat:appcompat` (Crucial para el sistema de idiomas)
    -   `com.google.android.material:material` (Necesaria para los temas de AppCompat)

## Puntos Clave y Aprendizajes del Proyecto

Este proyecto fue un ejercicio práctico para solucionar problemas comunes en el desarrollo de Android con Compose:

1.  **Gestión de Estado Global**: Se implementó un `object` (`ThemeState`, `LocaleManager`) con `MutableStateFlow` para gestionar estados que afectan a toda la aplicación, como el tema y el idioma.

2.  **Cambio de Idioma en Tiempo Real**: La solución a este desafío requirió una combinación de técnicas:
    -   Hacer que la `MainActivity` herede de `AppCompatActivity` en lugar de `ComponentActivity`.
    -   Usar `AppCompatDelegate.setApplicationLocales()` para aplicar el cambio de idioma a nivel de aplicación.
    -   Configurar el `AndroidManifest.xml` con `android:configChanges` para evitar que la `Activity` se recree con un parpadeo.
    -   Centralizar la lógica de cambio de idioma en un `object` (`LocaleHelper`).

3.  **Compatibilidad entre Vistas y Compose**: Fue necesario añadir la librería de `Material Components` para vistas y ajustar los temas en `themes.xml` para asegurar la compatibilidad con `AppCompatActivity`, incluso en un proyecto 100% Compose.

    
