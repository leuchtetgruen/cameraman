package de.leuchtetgruen.cameraman.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.api.RetrofitInstance
import de.leuchtetgruen.cameraman.navigation.Screen

class LoginViewModel : ViewModel() {
    var navController : NavController? = null
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var loading by mutableStateOf(false)
    var hasError by mutableStateOf(false)

    suspend fun login() {
        loading = true;
        val success = RetrofitInstance.login(username, password)
        hasError = !success
        if (success) {
            navController?.navigate(Screen.MapScreen.route)
        }
        loading = false
    }

}