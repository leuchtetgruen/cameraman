package de.leuchtetgruen.cameraman.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.leuchtetgruen.cameraman.businessobjects.CurrentUser
import de.leuchtetgruen.cameraman.presentation.login.LoginScreen
import de.leuchtetgruen.cameraman.presentation.map.MapScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val startScreen = if (CurrentUser.isLoggedIn()) {
        Screen.MapScreen.route
    }
    else {
        Screen.LoginScreen.route
    }
    NavHost(navController = navController, startDestination = startScreen ) {
        composable(route = Screen.MapScreen.route) {
            MapScreen(navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
    }
}