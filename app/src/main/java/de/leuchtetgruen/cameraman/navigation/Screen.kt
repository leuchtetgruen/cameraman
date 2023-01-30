package de.leuchtetgruen.cameraman.navigation

sealed class Screen(val route : String) {
    object LoginScreen : Screen("login_screen")
    object MapScreen : Screen("map_screen")
    object ShotScreen : Screen("shot_screen/{shot_id}") {
        fun routeWithId(id: Number) : String {
            val route = this.route.replace("{shot_id}", id.toString())
            return route
        }
    }

    object AddSourceScreen : Screen("add_source")

    object ShowScriptsScreen : Screen("show_scripts")
}
