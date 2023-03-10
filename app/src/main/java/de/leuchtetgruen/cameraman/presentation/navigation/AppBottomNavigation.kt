package de.leuchtetgruen.cameraman.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.navigation.Screen

@Composable
fun AppBottomNavigation(navController: NavController) {

    BottomNavigation(elevation = 16.dp) {
        BottomNavigationItem(selected = navController.currentDestination?.route == Screen.MapScreen.route,
            onClick = {
                navigateTo(navController, Screen.MapScreen.route)
            },
            icon = { Icon(Icons.Filled.PhotoCamera, contentDescription = "Camera") },
            label = { Text(stringResource(R.string.bottom_bar_tab_record_title))}
        )

        BottomNavigationItem(selected = navController.currentDestination?.route == Screen.AddSourceScreen.route,
            onClick = {
                navigateTo(navController, Screen.AddSourceScreen.route)
            },
            icon = { Icon(Icons.Filled.MenuBook, contentDescription = "Book")},
            label = { Text(stringResource(R.string.bottom_bar_tab_research_title))}
            )

        BottomNavigationItem(selected = navController.currentDestination?.route == Screen.ShowScriptsScreen.route,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.Mic, contentDescription = "Microphone")},
            label = { Text(stringResource(R.string.bottom_bar_tab_voiceover_title))}
        )
    }
}

fun navigateTo(navController: NavController, route : String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }

        launchSingleTop = true

        restoreState = true
    }
}