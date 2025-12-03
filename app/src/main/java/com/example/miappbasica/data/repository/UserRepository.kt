package com.example.miappbasica.data.repository

import com.example.miappbasica.data.local.dao.UserDao
import com.example.miappbasica.data.local.entity.User

/**
 * Repositorio que maneja las operaciones de datos para los Usuarios.
 * Oculta la fuente de datos (en este caso, Room) del resto de la app.
 * @param userDao El DAO para acceder a la tabla de usuarios.
 */
class UserRepository(private val userDao: UserDao) {

    /**
     * Inserta un nuevo usuario en la base de datos.
     * Esta es una función suspendida porque Room ejecuta las operaciones de BD
     * en un hilo de fondo.
     */
    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    /**
     * Busca un usuario por su email. Útil para comprobar si un usuario ya existe.
     * @param email El email del usuario a buscar.
     * @return El objeto User si se encuentra, o null si no existe.
     */
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }


}


