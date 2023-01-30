package de.leuchtetgruen.cameraman.presentation.map

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import de.leuchtetgruen.cameraman.businessobjects.ShotDescription
import de.leuchtetgruen.cameraman.data.ShotDescriptionRepository
import de.leuchtetgruen.cameraman.navigation.Screen
import kotlinx.coroutines.launch

class MapsViewModel : ViewModel() {
    var showDoneItems = mutableStateOf(false)
    var navController : NavController? = null
    var shotDescriptions = mutableStateListOf<ShotDescription>()
    var centeredLatLng = mutableStateOf(LatLng(52.0, 13.0))

    fun load() {
        viewModelScope.launch {
            shotDescriptions.clear()
            val shotDescriptionsApi = ShotDescriptionRepository.loadShotDescriptions().filter {
                it.hasLocation() && (showDoneItems.value || !it.done)
            }
            shotDescriptions.addAll(shotDescriptionsApi)

            centeredLatLng.value =  boundsForShotDescriptions().center
        }
    }

    fun selectedShotOnMap(shotDescription: ShotDescription) {
        navController?.navigate(Screen.ShotScreen.routeWithId(shotDescription.id))
    }

    fun boundsForShotDescriptions() : LatLngBounds {
        val builder = LatLngBounds.builder()
        shotDescriptions.forEach {
            if (it.lat != 0.0) {
                builder.include(LatLng(it.lat, it.lng))
            }
        }
        return builder.build()
    }
}