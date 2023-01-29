package de.leuchtetgruen.cameraman.presentation.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

/*
https://www.youtube.com/watch?v=0rc75uR0CNs
 */


@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {

    viewModel.load()

    Column(Modifier.fillMaxSize()) {
        val uiSettings by remember {
            mutableStateOf(MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = true))
        }
        val properties by remember {
          mutableStateOf(MapProperties(isMyLocationEnabled = false))
        }


        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(viewModel.centeredLatLng.value, 10f)
        }

        viewModel.navController = navController

        val coroutineScope = rememberCoroutineScope()
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState
        ) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(viewModel.centeredLatLng.value, 12.0f)


            viewModel.shotDescriptions.forEach {
                val shotDescription = it
                Marker(
                    state = MarkerState(position = LatLng(it.lat, it.lng)),
                    title = "Shot",
                    snippet = it.description,
                    onInfoWindowClick = {
                        coroutineScope.launch {
                            viewModel.selectedShotOnMap(shotDescription)
                        }
                    },
                )
            }
        }


    }
}