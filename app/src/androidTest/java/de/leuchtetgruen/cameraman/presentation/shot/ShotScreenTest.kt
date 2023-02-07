package de.leuchtetgruen.cameraman.presentation.shot

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.di.AppModules
import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.mocks.api.FakeCousteauApi
import de.leuchtetgruen.cameraman.mocks.repository.FakeShotDescriptionRepository
import de.leuchtetgruen.cameraman.ui.theme.CameraManTheme
import de.leuchtetgruen.cameraman.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModules::class)
class ShotScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController : NavHostController
    val objDone = ShotDescription(1, "descr", true, 0.0, 0.0, null, null)
    val objTodo = ShotDescription(1, "descr", false, 0.0, 0.0, null, null)
    @Before
    fun setUp() {
        FakeCousteauApi.shouldDelay = true
        hiltRule.inject()


    }

    fun prepareContent() {
        composeRule.activity.setContent {
            CameraManTheme() {
                navController = rememberNavController()
                ShotScreen(navController = navController, id = "1")
            }
        }
    }

    fun prepareTodoObject() {
        FakeShotDescriptionRepository.shots = mutableListOf(objTodo)
        prepareContent()
    }

    fun prepareDoneObject() {
        FakeShotDescriptionRepository.shots = mutableListOf(objDone)
        prepareContent()
    }


    @Test
    fun shows_title() {
        prepareTodoObject()
        composeRule.onNodeWithTag(TestTags.TAG_SHOT_TITLE).assertIsDisplayed()
    }

    @Test
    fun shows_right_title() {
        val ctx =composeRule.activity.applicationContext
        prepareTodoObject()
        composeRule.onNodeWithTag(TestTags.TAG_SHOT_TITLE).assertTextContains(ctx.getString(objDone.titleStringId()))
    }

    @Test
    fun shows_description_if_present() {
        prepareTodoObject()
        composeRule.onNodeWithTag(TestTags.TAG_DESCRIPTION).assertIsDisplayed()
    }

    @Test
    fun shows_todo_btn_if_not_done() {
        prepareTodoObject()
        composeRule.onNodeWithTag(TestTags.TAG_TODO_BTN).assertIsDisplayed()
    }

    @Test
    fun shows_done_btn_if_done() {
        prepareDoneObject()
        composeRule.onNodeWithTag(TestTags.TAG_DONE_BTN).assertIsDisplayed()
    }

}