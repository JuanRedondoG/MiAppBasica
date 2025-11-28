package com.example.miappbasica.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {
    // --- OPERACIONES BÁSICAS ---
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLogin(login: LoginEntity)

    @Query("SELECT * FROM LoginEntity WHERE email = :email LIMIT 1")
    suspend fun getLoginByEmail(email: String): LoginEntity?

    @Query("SELECT * FROM LoginEntity WHERE email = :email AND password = :password LIMIT 1")
    suspend fun buscarUsuario(email: String, password: String): LoginEntity?

    // --- PRIMITIVAS PARA LA LÓGICA DE LOGIN ---
    // Estas son las funciones simples que usaremos para construir la transacción en el ViewModel.
    // La función 'setLoggedIn' con cuerpo se elimina completamente de aquí.

    @Query("UPDATE LoginEntity SET isLoggedIn = 0")
    suspend fun clearLoggedInStatus()

    @Query("UPDATE LoginEntity SET isLoggedIn = 1 WHERE id = :userId")
    suspend fun updateUserAsLoggedIn(userId: Int)

    // 👇👇 NUEVA FUNCIÓN AÑADIDA 👇👇
    @Query("SELECT * FROM LoginEntity")
    fun getAllUsers(): Flow<List<LoginEntity>>


}
/**
 * Componente,Tipo de Código,Significado en Español
 * Flow,Tipo Base,Es un flujo o stream de datos asíncrono. Se utiliza para emitir múltiples valores
 * secuencialmente a lo largo del tiempo (como una tubería de datos vivos).
 *
 * <List<LoginEntity>>,Tipo de Contenido,"Especifica el tipo de datos que emite el flujo. Cada emisión
 * será una Lista de objetos, donde cada objeto es un LoginEntity (una entidad o registro de inicio de sesión)."
 *
 * ?,Operador de Nulabilidad,"Significa que todo el objeto Flow puede ser nulo (null). Es decir, la
 * variable puede contener un flujo de datos activo o no contener nada."
 *
 * ques,Nombre,"Es el nombre de la variable, parámetro o retorno al que se le asigna este tipo (
 * es probable que sea una abreviatura o un marcador de posición)."
 */
