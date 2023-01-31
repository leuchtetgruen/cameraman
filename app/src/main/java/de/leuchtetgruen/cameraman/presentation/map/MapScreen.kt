package de.leuchtetgruen.cameraman.presentation.map

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.presentation.navigation.AppBottomNavigation
import de.leuchtetgruen.cameraman.ui.theme.FontFamilyHeadline
import kotlinx.coroutines.launch

/*
https://www.youtube.com/watch?v=0rc75uR0CNs
 */


@Composable
fun MapContent(
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

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.toggleShowAllItems() }) {
                if (viewModel.showDoneItems.value) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_filter_alt_off_24),
                        contentDescription = "Should not show all items"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
                        contentDescription = "Should show all items"
                    )
                }

            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = { AppBottomNavigation(navController) }
    ) {
        System.out.println(it)

        val uiSettings by remember {
            mutableStateOf(
                MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true
                )
            )
        }
        val properties by remember {
            mutableStateOf(MapProperties(isMyLocationEnabled = true))
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
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(viewModel.centeredLatLng.value, 12.0f)




            viewModel.shotDescriptions.forEach {
                val shotDescription = it
                Marker(
                    state = MarkerState(position = LatLng(it.lat, it.lng)),
                    title = it.title(LocalContext.current),
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(navController: NavController) {


    // Camera permission state
    val fineLocationpermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    if (fineLocationpermissionState.status.isGranted) {
        MapContent(navController = navController)
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pen_48_red),
                contentDescription = "Camera reel",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
            )

            Text(
                "Cousteau",
                fontSize = 48.sp,
                modifier = Modifier.padding(16.dp),
                fontFamily = FontFamilyHeadline
            )

            val textToShow = if (fineLocationpermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "Um dir Aufnahmen in deiner Nähe anzuzeigen, muss die App auf deinen Standort zugreifen"
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Um die App zu nutzen, musst du die Berechtigung für den Standortzugriff geben "
            }
            Text(textToShow,
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), textAlign = TextAlign.Center)
            Button(onClick = { fineLocationpermissionState.launchPermissionRequest() }) {
                Text("Berechtigungsdialog anzeigen")
            }
        }
    }
}