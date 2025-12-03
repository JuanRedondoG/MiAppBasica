package com.example.miappbasica.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.miappbasica.data.local.dao.UserDao
import com.example.miappbasica.data.local.entity.User

@Database(entities = [User::class], version = 2, exportSchema = false)
// 1. Renombrar la clase aquí
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        // 2. Actualizar el tipo de la instancia
        private var INSTANCE: AppDatabase? = null

        // 3. Actualizar el tipo de retorno de la función
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, // 4. Usar la clase renombrada aquí
                    "app_database" // 5. (Opcional) Cambiar el nombre del archivo de la BD
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
