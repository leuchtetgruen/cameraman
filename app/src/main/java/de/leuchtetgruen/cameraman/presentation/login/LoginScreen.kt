package de.leuchtetgruen.cameraman.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.api.RetrofitInstance
import de.leuchtetgruen.cameraman.navigation.Screen
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val coroutineScope = rememberCoroutineScope()

        var username by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }

        var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }

         fun doLogin() {
             coroutineScope.launch {
                val success = RetrofitInstance.login(username.text, password.text)
                 if (success) {
                     navController.navigate(Screen.MapScreen.route)
                 }
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
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(16.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = { doLogin() },
            modifier = Modifier.padding(16.dp)) {
            Text("Login")
        }
    }
}