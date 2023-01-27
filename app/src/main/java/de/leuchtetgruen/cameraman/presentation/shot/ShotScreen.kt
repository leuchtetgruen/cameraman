package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ShotScreen(navController: NavController,
               id: String,
               viewModel: ShotViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    viewModel.navController = navController
    viewModel.loadShot(id.toInt())
    
    Column(        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {


        val description = if (viewModel.shotDescription != null) viewModel.shotDescription?.description else "foo"
        Text(text = description ?: "bar")
    }
}