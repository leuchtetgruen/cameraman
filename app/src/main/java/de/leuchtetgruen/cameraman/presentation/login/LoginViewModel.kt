package de.leuchtetgruen.cameraman.presentation.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leuchtetgruen.cameraman.api.RuntimeTokenStore
import de.leuchtetgruen.cameraman.navigation.Screen
import de.leuchtetgruen.cameraman.presentation.use_cases.Login
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: Login, val runtimeTokenStore: RuntimeTokenStore) : ViewModel() {
    var navController : NavController? = null
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var loading by mutableStateOf(false)
    var hasError by mutableStateOf(false)

    suspend fun login(context : Context) {
        loading = true
        val success = loginUseCase(username, password, context)
        hasError = !success
        if (success) {
            navController?.navigate(Screen.MapScreen.route)
        }
        loading = false
    }

}