package de.leuchtetgruen.cameraman.presentation.map

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import de.leuchtetgruen.cameraman.R
import kotlinx.coroutines.launch

/*
https://www.youtube.com/watch?v=0rc75uR0CNs
 */


@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    viewModel.load()

    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect {
            Toast.makeText(ctx, it, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.toggleShowAllItems() }) {
            if (viewModel.showDoneItems.value) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_filter_alt_off_24), contentDescription = "Should not show all items" )
            }
            else {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24), contentDescription = "Should show all items" )
            }

        }
    }, floatingActionButtonPosition = FabPosition.End) {
        System.out.println(it)

        val uiSettings by remember {
            mutableStateOf(MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = true))
        }
        val properties by remember {
            mutableStateOf(MapProperties(isMyLocationEnabled = false))
        }


        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(viewModel.centeredLatLng.value, 10f)
        }

        viewModel.navController = navController


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