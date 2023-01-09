package de.leuchtetgruen.cameraman.presentation.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

/*
https://www.youtube.com/watch?v=0rc75uR0CNs
 */

@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = viewModel.floatingButtonImageVector(), contentDescription = "Toggle done shots" )
            }
        }
    ) {
        val uiSettings by remember {
            mutableStateOf(MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled = false))
        }
        val properties by remember {
          mutableStateOf(MapProperties(isMyLocationEnabled = false))
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            uiSettings = uiSettings
        )
    }
}