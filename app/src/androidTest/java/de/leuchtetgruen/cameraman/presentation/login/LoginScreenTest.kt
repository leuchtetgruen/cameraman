package de.leuchtetgruen.cameraman.presentation.login

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.di.AppModules
import de.leuchtetgruen.cameraman.navigation.Screen
import de.leuchtetgruen.cameraman.ui.theme.CameraManTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModules::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CameraManTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }

                }
            }
        }
    }

    @Test
    fun has_username_field() {
        composeRule.onNodeWithTag("username").assertIsDisplayed()
    }

    @Test
    fun has_password_field() {
        composeRule.onNodeWithTag("password").assertIsDisplayed()
    }

    @Test
    fun has_login_button() {
        composeRule.onNodeWithTag("login_button").assertIsDisplayed()
    }

    @Test
    fun clicking_login_shows_loading_spinner() {
        composeRule.onNodeWithTag("login_button").performClick()

        composeRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun clicking_login_shows_hides_button() {
        composeRule.onNodeWithTag("login_button").performClick()

        composeRule.onNodeWithTag("login_button").assertDoesNotExist()
    }
}