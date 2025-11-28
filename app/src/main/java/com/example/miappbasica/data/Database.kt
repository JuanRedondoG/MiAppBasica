package com.example.miappbasica.data



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Clase principal de la base de datos de Room.
 *
 * @Database - Anotación que define la clase como una base de datos Room.
 * entities - Array de todas las clases de entidad que pertenecen a esta base de datos.
 * version - El número de versión de la base de datos. Debe incrementarse cada vez que cambies el esquema.
 * exportSchema - Es una buena práctica dejarlo en 'false' para proyectos de desarrollo para no guardar
 * el historial del esquema.
 */
@Database(
    entities = [LoginEntity::class], // Aquí se listan todas las tablas (entidades)
    version = 1,                      // Incrementa esto si cambias la estructura de la tabla
    exportSchema = false
)
abstract class ComicDatabase : RoomDatabase() {

    // Declara una función abstracta para cada DAO que tenga la base de datos.
    // Room generará la implementación por nosotros.
    abstract fun comicDao(): ComicDao

    // El 'companion object' permite acceder a los métodos para crear u obtener la base de datos
    // sin necesidad de instanciar la clase. Es el patrón Singleton.
    companion object {
        // La anotación @Volatile asegura que el valor de INSTANCE sea siempre el más actualizado
        // y visible para todos los hilos de ejecución.
        @Volatile
        private var INSTANCE: ComicDatabase? = null

        /**
         * Función para obtener la instancia única de la base de datos.
         * Utiliza un patrón Singleton para prevenir que se abran múltiples instancias
         * de la base de datos al mismo tiempo, lo cual es costoso.
         */
        fun getDatabase(context: Context): ComicDatabase {
            // 'synchronized' asegura que solo un hilo pueda ejecutar este bloque de código a la vez,
            // evitando que se creen dos instancias de la base de datos si dos hilos la piden al mismo tiempo.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ComicDatabase::class.java,
                    "comic_database" // Nombre del archivo de la base de datos en el dispositivo
                )
                    // Aquí se pueden añadir estrategias de migración en el futuro con .addMigrations()
                    .build()

                INSTANCE = instance
                // Devuelve la instancia recién creada
                instance
            }
        }
    }
}
