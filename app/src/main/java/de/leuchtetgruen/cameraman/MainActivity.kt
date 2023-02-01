package de.leuchtetgruen.cameraman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.AndroidEntryPoint
import de.leuchtetgruen.cameraman.businessobjects.TokenProvider
import de.leuchtetgruen.cameraman.navigation.Navigation
import de.leuchtetgruen.cameraman.presentation.loading.LoadingScreenViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var loading = mutableStateOf(true)
        val error = mutableStateOf("")

        if (TokenProvider.hasRefreshToken(this)) {
            val refreshToken = TokenProvider.getRefreshToken(this)
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
                    LoadingScreenViewModel(loading = loading, error=error)
                }
                else {
                    Navigation()
                }
            }
        }
    }
}




