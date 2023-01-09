package de.leuchtetgruen.cameraman.presentation.map

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel

class MapsViewModel : ViewModel() {
    var state by mutableStateOf(MapState())

    fun floatingButtonImageVector() : ImageVector {
        return if (state.shouldShowDoneJobs) {
            Icons.Default.CheckCircle
        }
        else {
            Icons.Default.List
        }
    }
}