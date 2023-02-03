package de.leuchtetgruen.cameraman.presentation.loading

import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.di.AppModules
import de.leuchtetgruen.cameraman.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModules::class)
class LoadingScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private var loading = mutableStateOf(false)
    private var error = mutableStateOf("")

    @Before
    fun setUp() {
        hiltRule.inject()
        loading.value = false
        error.value = ""
        composeRule.activity.setContent {
            LoadingScreen(loading = loading, error = error)
        }
    }

    @Test
    fun setting_loading_will_show_loading_text() {
        loading.value = true

        composeRule.onNodeWithTag(TestTags.TAG_LOADING).assertIsDisplayed()
    }

    @Test
    fun setting_loading_will_show_loading_indicator() {
        loading.value = true

        composeRule.onNodeWithTag(TestTags.TAG_LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun setting_error_while_show_error_value() {
        val expectedText = "fanquackdabl"
        error.value = expectedText

        composeRule.onNodeWithTag(TestTags.TAG_ERROR_MESSAGE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TAG_ERROR_MESSAGE).assertTextEquals(expectedText)
    }

    @Test
    fun setting_error_while_show_error_text_2() {
        error.value = "anyoldvaluewilldo"

        composeRule.onNodeWithTag(TestTags.TAG_ERROR_MESSAGE_2).assertIsDisplayed()
    }
}