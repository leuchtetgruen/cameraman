package de.leuchtetgruen.cameraman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.leuchtetgruen.cameraman.api.RetrofitInstance
import de.leuchtetgruen.cameraman.businessobjects.TokenProvider
import de.leuchtetgruen.cameraman.navigation.Navigation
import de.leuchtetgruen.cameraman.ui.theme.CameraManTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (TokenProvider.hasRefreshToken(this)) {
            val refreshToken = TokenProvider.getRefreshToken(this)
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    RetrofitInstance.eventuallyRefreshToken(refreshToken)
                }
            }
        }
        setContent {
            CameraManTheme {
                Navigation()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CameraManTheme {
        Navigation()
    }
}