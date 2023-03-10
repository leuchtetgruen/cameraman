package de.leuchtetgruen.cameraman.presentation.map

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.navigation.Screen
import de.leuchtetgruen.cameraman.presentation.use_cases.GetShotDescriptions
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(val getShotDescriptions: GetShotDescriptions): ViewModel() {

    var showDoneItems = mutableStateOf(false)
    var navController : NavController? = null
    var shotDescriptions = mutableStateListOf<ShotDescription>()
    var centeredLatLng = mutableStateOf(LatLng(52.0, 13.0))

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun load() {
        viewModelScope.launch {
            shotDescriptions.clear()
            val shotDescriptionsApi = getShotDescriptions(showDoneItems.value)
            shotDescriptions.addAll(shotDescriptionsApi)

            val bounds = boundsForShotDescriptions()
            if (bounds != null) {
                centeredLatLng.value =  bounds.center
            }
        }
    }

    fun selectedShotOnMap(shotDescription: ShotDescription) {
        navController?.navigate(Screen.ShotScreen.routeWithId(shotDescription.id))
    }

    fun boundsForShotDescriptions() : LatLngBounds? {
        if (shotDescriptions.isEmpty()) return null

        val builder = LatLngBounds.builder()
        shotDescriptions.forEach {
            if (it.lat != 0.0) {
                builder.include(LatLng(it.lat, it.lng))
            }
        }
        return builder.build()
    }

    fun toggleShowAllItems() {
        this.showDoneItems.value = !this.showDoneItems.value
        load()

        val msg =  if (this.showDoneItems.value) "Zeige alle Aufnahmen" else "Zeige nur unerledigte Aufnahmen"

        viewModelScope.launch {
            _toastMessage.emit(msg)
        }


    }
}