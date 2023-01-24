package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ShotScreen(navController: NavController,
               viewModel: ShotViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    viewModel.navController = navController
    
    Column(        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        
    }
}