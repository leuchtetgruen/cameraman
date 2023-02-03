package de.leuchtetgruen.cameraman.presentation.map

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.di.AppModules
import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.mocks.api.FakeCousteauApi
import de.leuchtetgruen.cameraman.mocks.repository.FakeShotDescriptionRepository
import de.leuchtetgruen.cameraman.navigation.Screen
import de.leuchtetgruen.cameraman.ui.theme.CameraManTheme
import de.leuchtetgruen.cameraman.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModules::class)
class MapScreenTest {

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
                    startDestination = Screen.MapScreen.route
                ) {
                    composable(route = Screen.MapScreen.route) {
                        MapScreen(navController)
                    }

                }
            }
        }
    }

    @Test
    fun markers_appear_on_map() {
        val obj1 = ShotDescription(0, "test_description", true, 0.0, 0.0, null, null)
        val obj2 = ShotDescription(1, "test_description", true, 10.0, 0.0, null, null)
        val obj3 = ShotDescription(2, "test_description", true, 0.0, 10.0, null, null)
        FakeShotDescriptionRepository.shots = mutableListOf<ShotDescription>(obj1, obj2, obj3)


        composeRule.onNodeWithTag(TestTags.TAG_MAP).assertIsDisplayed()
        // there seems to be no way to access the actual markers. they also dont appear in the tree. wtf?
    }
}