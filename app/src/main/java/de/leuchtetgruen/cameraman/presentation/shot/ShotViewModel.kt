package de.leuchtetgruen.cameraman.presentation.shot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.domain.repository.ShotDescriptionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShotViewModel @Inject constructor(val shotDescriptionRepository: ShotDescriptionRepository) : ViewModel() {




    var navController : NavController? = null
    var shotDescription by mutableStateOf<ShotDescription?>(null)
    var saving by mutableStateOf(false)

    fun loadShot(id : Int) {
        viewModelScope.launch {
            shotDescription = shotDescriptionRepository.getShotWithId(id)
        }
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