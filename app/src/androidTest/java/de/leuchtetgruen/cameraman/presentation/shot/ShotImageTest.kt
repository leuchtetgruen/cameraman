package de.leuchtetgruen.cameraman.presentation.shot

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.di.AppModules
import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModules::class)
class ShotImageTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()



    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun existing_image_url_leads_to_showing_actual_image() {
        val imageUrl = "http://test.de/test.jpg"
        val shotDescription = ShotDescription(0, "", false, 0.0, 0.0, imageUrl, null)
        val viewModel = ShotImageViewModel(shotDescription)
        composeRule.activity.setContent {
            ShotImage(viewModel = viewModel)
        }

        composeRule.onNodeWithTag(TestTags.TAG_ACTUAL_IMAGE).assertExists()
    }

    @Test
    fun non_existing_image_url_leads_to_showing_placeholder_image() {
        val shotDescription = ShotDescription(0, "", false, 0.0, 0.0, null, null)
        val viewModel = ShotImageViewModel(shotDescription)
        composeRule.activity.setContent {
            ShotImage(viewModel = viewModel)
        }

        composeRule.onNodeWithTag(TestTags.TAG_PLACEHOLDER_IMAGE).assertExists()
    }

}