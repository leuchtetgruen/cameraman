package de.leuchtetgruen.cameraman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

        var loading = mutableStateOf(true)

        if (TokenProvider.hasRefreshToken(this)) {
            val refreshToken = TokenProvider.getRefreshToken(this)
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    RetrofitInstance.eventuallyRefreshToken(refreshToken)
                    loading.value = false
                }
            }
        }
        setContent {
            CameraManTheme {
                if (loading.value) {
                    LoadingSpinner()
                }
                else {
                    Navigation()
                }

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

@Composable
fun LoadingSpinner() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Connecting...", modifier = Modifier.padding(16.dp))
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))

    }
}