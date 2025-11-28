package com.example.miappbasica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miappbasica.data.ComicDao
import java.lang.IllegalArgumentException

/**
 * Factory (fábrica) para crear instancias de LoginViewModel.
 *
 * ¿Por qué es necesaria?
 * Por defecto, Android solo sabe crear ViewModels con constructores vacíos.
 * Nuestro LoginViewModel necesita un 'comicDao' para funcionar, así que
 * esta clase le "enseña" al sistema cómo construirlo.
 */
// 👇👇 LA CORRECCIÓN CLAVE ESTÁ AQUÍ: EL CONSTRUCTOR AHORA ACEPTA EL comicDao 👇👇
class LoginViewModelFactory(private val comicDao: ComicDao) : ViewModelProvider.Factory {

    /**
     * Este método es llamado por el framework cuando necesita crear el ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Comprueba si la clase que se pide crear es LoginViewModel.
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            // Si coincide, crea la instancia pasándole el dao que recibimos en el constructor.
            // La advertencia "UNCHECKED_CAST" es segura por la comprobación anterior.
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(comicDao) as T
        }
        // Si se intenta usar esta factory para otro ViewModel, lanza un error claro.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

