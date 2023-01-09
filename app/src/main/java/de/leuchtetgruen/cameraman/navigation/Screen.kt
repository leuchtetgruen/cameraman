package de.leuchtetgruen.cameraman.navigation

sealed class Screen(val route : String) {
    object LoginScreen : Screen("login_screen")
    object MapScreen : Screen("map_screen")
}
