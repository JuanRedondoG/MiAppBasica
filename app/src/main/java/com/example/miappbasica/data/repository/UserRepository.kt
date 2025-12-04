package com.example.miappbasica.data.repository

import com.example.miappbasica.data.local.dao.UserDao
import com.example.miappbasica.data.local.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que maneja las operaciones de datos para los Usuarios.
 * Oculta la fuente de datos (en este caso, Room) del resto de la app.
 * @param userDao El DAO para acceder a la tabla de usuarios.
 */
class UserRepository(private val userDao: UserDao) {

    /**
     * Inserta un nuevo usuario en la base de datos.
     */
    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    /**
     * Busca un usuario por su email.
     * @param email El email del usuario a buscar.
     * @return El objeto User si se encuentra, o null si no existe.
     */
    suspend fun findUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    /**
     * Obtiene un flujo con todos los usuarios.
     */
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
}


