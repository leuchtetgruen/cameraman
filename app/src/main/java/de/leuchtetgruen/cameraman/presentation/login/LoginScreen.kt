package de.leuchtetgruen.cameraman.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.ui.theme.FontFamilyHeadline
import de.leuchtetgruen.cameraman.util.TestTags
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController,
viewModel: LoginViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize().testTag(TestTags.TAG_LOGIN_SCREEN),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        viewModel.navController = navController

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        fun doLogin() {
            coroutineScope.launch {
                viewModel.login(context)
            }
        }

        Icon(painter = painterResource(id = R.drawable.pen_48_red),
            contentDescription = "Camera reel",
            modifier = Modifier
                .width(48.dp)
                .height(48.dp) )

        Text("Cousteau",
            fontSize = 48.sp,
            modifier = Modifier.padding(16.dp),
            fontFamily = FontFamilyHeadline)

        TextField(
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = { Text(stringResource(R.string.username)) },
            placeholder = { Text(stringResource(R.string.username))},
            modifier = Modifier
                .padding(16.dp)
                .testTag(TestTags.TAG_USERNAME),
        )

        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(16.dp)
                .testTag(TestTags.TAG_PASSWORD)
        )

        if (viewModel.hasError && !viewModel.loading) {
            Text("Could not login successfully", modifier = Modifier.padding(16.dp).testTag(TestTags.TAG_ERROR_MESSAGE), color = Color.Red)
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(CenterHorizontally)) {
            if (viewModel.loading) {
                CircularProgressIndicator(modifier = Modifier
                    .padding(16.dp)
                    .testTag(TestTags.TAG_LOADING_INDICATOR))
            }
            else {
                Button(
                    onClick = { doLogin() },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .testTag(TestTags.TAG_LOGIN_BUTTOM),
                ) {
                    Text("Login")
                }
            }


        }

    }
}