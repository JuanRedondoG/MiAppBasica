package com.example.miappbasica.ui.screen.login



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miappbasica.data.repository.UserRepository
import com.example.miappbasica.ui.screen.LoginViewModel

// Esta clase le enseña al sistema cómo crear un LoginViewModel.
class LoginViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    // El sistema llamará a esta función cuando necesite crear el ViewModel.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            // Si nos piden un LoginViewModel, lo creamos y le pasamos el repositorio.
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userRepository) as T
        }
        // Si nos piden cualquier otro ViewModel, lanzamos un error.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
