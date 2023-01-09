package de.leuchtetgruen.cameraman.presentation.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.api.RetrofitInstance
import de.leuchtetgruen.cameraman.businessobjects.TokenProvider
import de.leuchtetgruen.cameraman.navigation.Screen

class LoginViewModel : ViewModel() {
    var navController : NavController? = null
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var loading by mutableStateOf(false)
    var hasError by mutableStateOf(false)

    suspend fun login(context : Context) {
        loading = true
        val success = RetrofitInstance.login(username, password)
        hasError = !success
        if (success) {
            RetrofitInstance.refreshToken?.let { TokenProvider.setRefreshToken(context, it) }
            navController?.navigate(Screen.MapScreen.route)
        }
        loading = false
    }

}