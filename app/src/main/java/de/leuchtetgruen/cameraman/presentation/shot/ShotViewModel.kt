package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.businessobjects.ShotDescription
import de.leuchtetgruen.cameraman.data.ShotDescriptionRepository

class ShotViewModel : ViewModel() {

    var navController : NavController? = null
    var shotDescription by mutableStateOf<ShotDescription?>(null)

    fun loadShot(id : Int) {
        val test = ShotDescriptionRepository.getShotWithId(id)
        shotDescription = test
    }

    fun markAsDone() {

    }

    fun markAsToDo() {

    }
}