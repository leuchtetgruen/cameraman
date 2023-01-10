package de.leuchtetgruen.cameraman.presentation.loading

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.leuchtetgruen.cameraman.R

@Composable
fun LoadingScreenViewModel(loading : MutableState<Boolean>, error : MutableState<String> = mutableStateOf("")) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (error.value.isNullOrBlank()) {
            Icon(painter = painterResource(id = R.drawable.camera_reels),
                contentDescription = "Camera reel",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp))
        } else {
            Icon(painter = painterResource(id = R.drawable.bug),
                contentDescription = "Bug",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp) )
        }

        if (loading.value) {
            Text("Loading...", modifier = Modifier.padding(16.dp))
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        if (!error.value.isNullOrBlank()) {
            Text(error.value, color = Color.Red, modifier = Modifier.padding(16.dp))

            Text("Try again later", color = Color.Gray, modifier = Modifier.padding(16.dp))
        }

    }
}


