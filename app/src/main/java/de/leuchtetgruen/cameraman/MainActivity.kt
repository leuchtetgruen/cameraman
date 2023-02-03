package de.leuchtetgruen.cameraman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.AndroidEntryPoint
import de.leuchtetgruen.cameraman.businessobjects.TokenProvider
import de.leuchtetgruen.cameraman.navigation.Navigation
import de.leuchtetgruen.cameraman.presentation.loading.LoadingScreen
import de.leuchtetgruen.cameraman.presentation.use_cases.EventuallyRefreshApiToken
import de.leuchtetgruen.cameraman.ui.theme.CameraManTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var eventuallyRefreshApiToken : EventuallyRefreshApiToken

    @Inject
    lateinit var tokenProvider : TokenProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val loading = mutableStateOf(true)
        val error = mutableStateOf("")

        if (tokenProvider.hasRefreshToken()) {
            val refreshToken = tokenProvider.getRefreshToken()
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    try {
                        eventuallyRefreshApiToken(refreshToken)
                    } catch (e : Exception) {
                        error.value = e.message!!
                    }
                    loading.value = false
                }
            }
        }
        else {
            loading.value = false
        }
        setContent {
            CameraManTheme {
                if (loading.value || !error.value.isBlank()) {
                    LoadingScreen(loading = loading, error=error)
                }
                else {
                    Navigation(tokenProvider)
                }
            }
        }
    }
}




