package com.example.miappbasica



import android.app.Application
import com.example.miappbasica.data.ComicDatabase

/**
 * Clase de aplicación personalizada para inicializar componentes globales.
 * Esta clase se ejecuta una sola vez cuando la aplicación se inicia.
 */
class ComicApplication : Application() {

    /**
     * Instancia de la base de datos que se creará de forma "perezosa" (lazy).
     *
     * 'lazy' es un delegado de propiedad de Kotlin que asegura que la inicialización
     * de 'ComicDatabase.getDatabase(this)' se ejecute solo la primera vez que se
     * acceda a la propiedad 'database'.
     *
     * Esto es eficiente y seguro para los hilos (thread-safe).
     */
    val database: ComicDatabase by lazy { ComicDatabase.getDatabase(this) }
}



