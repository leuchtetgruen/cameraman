package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import de.leuchtetgruen.cameraman.businessobjects.ShotDescription
import de.leuchtetgruen.cameraman.di.BasicDI
import kotlinx.coroutines.launch

class ShotViewModel : ViewModel() {

    // TODO later change to actual DI
    val shotDescriptionRepository = BasicDI.shotDescriptionRepository


    var navController : NavController? = null
    var shotDescription by mutableStateOf<ShotDescription?>(null)
    var saving by mutableStateOf(false)

    fun loadShot(id : Int) {
        val test = shotDescriptionRepository.getShotWithId(id)
        shotDescription = test
    }

    fun markAsDone() {
        viewModelScope.launch {
            saving = true
            shotDescriptionRepository.changeDoneState(shotDescription!!, true)
            saving = false
            navController?.popBackStack()
        }
    }

    fun markAsToDo() {
        viewModelScope.launch {
            saving = true
            shotDescriptionRepository.changeDoneState(shotDescription!!, false)
            saving = false
            navController?.popBackStack()
        }
    }
}