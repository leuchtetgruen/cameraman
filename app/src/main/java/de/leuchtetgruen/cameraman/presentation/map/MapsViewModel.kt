package de.leuchtetgruen.cameraman.presentation.map

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import de.leuchtetgruen.cameraman.businessobjects.ShotDescription
import de.leuchtetgruen.cameraman.data.ShotDescriptionRepository
import kotlinx.coroutines.launch

class MapsViewModel : ViewModel() {
    var shotDescriptions = mutableStateListOf<ShotDescription>()
    var centeredLatLng = mutableStateOf(LatLng(52.0, 13.0))

    fun load() {
        viewModelScope.launch {
            shotDescriptions.clear()
            val shotDescriptionsApi = ShotDescriptionRepository.loadShotDescriptions().filter { it.hasLocation() }
            shotDescriptions.addAll(shotDescriptionsApi)
        }
    }

    fun moveToPositionOfShot(shotDescription: ShotDescription) {
        centeredLatLng.value = LatLng(shotDescription.lat, shotDescription.lng)
    }
}