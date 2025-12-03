package com.example.miappbasica.data.local.dao

import androidx.room.*
import com.example.miappbasica.data.local.entity.User
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

//importaciones añadidas
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user_table ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserById(userId: Int): Flow<User>


    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User? // Devuelve un usuario o null si no lo encuentra



    // --- FUNCIÓN NUEVA ---
    /**
     * Busca un usuario por su dirección de email.
     * Se usa LIMIT 1 para asegurar que solo devuelva un resultado, ya que el email debe ser único.
     */
    //@Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    //suspend fun getUserByEmail(email: String): User?
}
