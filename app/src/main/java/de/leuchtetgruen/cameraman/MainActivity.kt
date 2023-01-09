package de.leuchtetgruen.cameraman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.leuchtetgruen.cameraman.navigation.Navigation
import de.leuchtetgruen.cameraman.ui.theme.CameraManTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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