package de.leuchtetgruen.cameraman.presentation.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

/*
https://www.youtube.com/watch?v=0rc75uR0CNs
 */

@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val scaffoldState = rememberScaffoldState()

    viewModel.load()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        val uiSettings by remember {
            mutableStateOf(MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = true))
        }
        val properties by remember {
          mutableStateOf(MapProperties(isMyLocationEnabled = false))
        }

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(viewModel.centeredLatLng.value, 10f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState
        ) {
            viewModel.shotDescriptions.forEach {
                val shotDescription = it
                Marker(
                    state = MarkerState(position = LatLng(it.lat, it.lng)),
                    title = "Shot",
                    snippet = it.description,
                    onInfoWindowClick = {
                        viewModel.moveToPositionOfShot(shotDescription)
                    }
                )
            }
        }
    }
}