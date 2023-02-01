package de.leuchtetgruen.cameraman.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.leuchtetgruen.cameraman.businessobjects.TokenProvider
import de.leuchtetgruen.cameraman.presentation.login.LoginScreen
import de.leuchtetgruen.cameraman.presentation.map.MapScreen
import de.leuchtetgruen.cameraman.presentation.shot.ShotScreen

@Composable
fun Navigation(tokenProvider: TokenProvider) {
    val navController = rememberNavController()

    val startScreen = if (tokenProvider.needsLogin()) {
        Screen.LoginScreen.route
    } else {
        Screen.MapScreen.route
    }

    NavHost(navController = navController, startDestination = startScreen ) {
        composable(route = Screen.MapScreen.route) {
            MapScreen(navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.ShotScreen.route, arguments = listOf(
            navArgument("shot_id") {
                type = NavType.StringType
                defaultValue = "0"
            }
        )) {
            val id = it.arguments?.getString("shot_id") ?: "-0"
            ShotScreen(navController = navController, id = id)
        }
    }
}