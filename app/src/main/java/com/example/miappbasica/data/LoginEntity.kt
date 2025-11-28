package com.example.miappbasica.data

import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.ColumnInfo
import androidx.room.Index

/**
 * Entidad de Room que representa la tabla de usuarios.
 * Cada registro es un usuario con sus credenciales.
 * Se añade un índice único al email para evitar duplicados y acelerar búsquedas.
 */
@Entity(
    tableName = "LoginEntity",
    indices = [Index(value = ["email"], unique = true)] // Evita que haya dos usuarios con el mismo email
)
data class LoginEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Puedes añadir un campo para el nombre del usuario si lo deseas
     //val nombre: String,
    val nombre: String = "",

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "password")
    val password: String,

    // Campo para saber si el usuario tiene la sesión iniciada.
    // 0 = false (no logueado), 1 = true (logueado)
    // Se inicializa en 0 por defecto.
    val isLoggedIn: Int = 0
)
