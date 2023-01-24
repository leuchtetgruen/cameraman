package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.businessobjects.ShotDescription

class ShotViewModel : ViewModel() {
    var navController : NavController? = null
    var shotDescription by mutableStateOf<ShotDescription?>(null)
}