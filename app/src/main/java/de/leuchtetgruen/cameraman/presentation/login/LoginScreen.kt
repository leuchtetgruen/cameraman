package de.leuchtetgruen.cameraman.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.R
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController,
viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        viewModel.navController = navController

        val coroutineScope = rememberCoroutineScope()

        fun doLogin() {
            coroutineScope.launch {
                viewModel.login()
            }
        }

        Icon(painter = painterResource(id = R.drawable.camera_reels),
            contentDescription = "Camera reel",
            modifier = Modifier
                .width(48.dp)
                .height(48.dp) )

        Text("CameraMan",
            fontSize = 48.sp,
            modifier = Modifier.padding(16.dp))

        TextField(
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(16.dp)
        )

        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(16.dp)
        )

        if (viewModel.hasError && !viewModel.loading) {
            Text("Could not login successfully", modifier = Modifier.padding(16.dp), color = Color.Red)
        }

        Row() {
            if (viewModel.loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            Button(
                onClick = { doLogin() },
                modifier = Modifier.padding(16.dp)) {
                Text("Login")
            }
        }

    }
}