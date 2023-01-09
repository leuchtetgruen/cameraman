package de.leuchtetgruen.cameraman.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.leuchtetgruen.cameraman.presentation.login.LoginScreen
import de.leuchtetgruen.cameraman.presentation.map.MapScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route ) {
        composable(route = Screen.MapScreen.route) {
            MapScreen(navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
    }
}