package de.leuchtetgruen.cameraman.presentation.login

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.mocks.api.FakeCousteauApi
import de.leuchtetgruen.cameraman.di.AppModules
import de.leuchtetgruen.cameraman.navigation.Screen
import de.leuchtetgruen.cameraman.presentation.map.MapScreen
import de.leuchtetgruen.cameraman.ui.theme.CameraManTheme
import de.leuchtetgruen.cameraman.util.TestTags
import org.junit.Assert
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

    private lateinit var navController : NavHostController

    @Before
    fun setUp() {
        FakeCousteauApi.shouldDelay = true
        hiltRule.inject()
        composeRule.activity.setContent {
            CameraManTheme() {
                navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }
                    composable(route = Screen.MapScreen.route) {
                        MapScreen(navController)
                    }

                }
            }
        }
    }

    @Test
    fun has_username_field() {
        composeRule.onNodeWithTag(TestTags.TAG_USERNAME).assertIsDisplayed()
    }

    @Test
    fun username_has_right_label() {
        val labelText = composeRule.activity.getString(R.string.username)
        composeRule.onNodeWithTag(TestTags.TAG_USERNAME).assertTextContains(labelText)
    }

    @Test
    fun has_password_field() {
        composeRule.onNodeWithTag(TestTags.TAG_PASSWORD).assertIsDisplayed()
    }

    @Test
    fun password_has_right_label() {
        val labelText = composeRule.activity.getString(R.string.password)
        composeRule.onNodeWithTag(TestTags.TAG_PASSWORD).assertTextContains(labelText)
    }

    @Test
    fun has_login_button() {
        composeRule.onNodeWithTag(TestTags.TAG_LOGIN_BUTTOM).assertIsDisplayed()
    }

    @Test
    fun clicking_login_shows_loading_spinner() {
        composeRule.onNodeWithTag(TestTags.TAG_LOGIN_BUTTOM).performClick()

        composeRule.onNodeWithTag(TestTags.TAG_LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun clicking_login_shows_hides_button() {
        composeRule.onNodeWithTag(TestTags.TAG_LOGIN_BUTTOM).performClick()

        composeRule.onNodeWithTag(TestTags.TAG_LOGIN_BUTTOM).assertDoesNotExist()
    }

    @Test
    fun failing_login_leads_to_errormessage() {
        FakeCousteauApi.loginShouldSucceed = false
        FakeCousteauApi.shouldDelay = false

        composeRule.onNodeWithTag(TestTags.TAG_LOGIN_BUTTOM).performClick()


        composeRule.onNodeWithTag(TestTags.TAG_ERROR_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun successful_login_leads_to_navigation_to_map() {
        FakeCousteauApi.loginShouldSucceed = true
        FakeCousteauApi.shouldDelay = false

        composeRule.onNodeWithTag(TestTags.TAG_LOGIN_BUTTOM).performClick()


        val route = navController.currentDestination?.route
        Assert.assertEquals(route, Screen.MapScreen.route)
    }
}