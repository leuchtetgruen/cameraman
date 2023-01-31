package de.leuchtetgruen.cameraman.domain.model

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import de.leuchtetgruen.cameraman.MainActivity
import de.leuchtetgruen.cameraman.R
import de.leuchtetgruen.cameraman.di.AppModules
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModules::class)
class ShotDescriptionInstrumentedTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun title_is_right_for_objects_with_location() {
        val shotDescriptionWithLocation = ShotDescription(1, "", false, 13.0, 52.0, null, null)

        val locationString = composeRule.activity.applicationContext.getString(R.string.on_spot_shot)
        Assert.assertEquals(locationString, shotDescriptionWithLocation.title(composeRule.activity.applicationContext))
    }

    @Test
    fun title_is_right_for_object_without_location_but_with_linked_media() {
        val shotWithoutLocationButWithLinkedMedia = ShotDescription(1, "", false, 0.0, 0.0, null, "linked.media")

        val locationString = composeRule.activity.applicationContext.getString(R.string.linked_medium)
        Assert.assertEquals(locationString, shotWithoutLocationButWithLinkedMedia.title(composeRule.activity.applicationContext))
    }

    @Test
    fun title_is_right_for_object_with_neither_location_nor_linked_media() {
        val shotWithoutLocationButWithLinkedMedia = ShotDescription(1, "", false, 0.0, 0.0, null, null)

        val locationString = composeRule.activity.applicationContext.getString(R.string.shot)
        Assert.assertEquals(locationString, shotWithoutLocationButWithLinkedMedia.title(composeRule.activity.applicationContext))
    }

    @Test
    fun title_is_right_for_objects_with_location_and_linked_media() {
        val shotDescriptionWithLocation = ShotDescription(1, "", false, 13.0, 52.0, null, "linked.medium")

        val locationString = composeRule.activity.applicationContext.getString(R.string.on_spot_shot)
        Assert.assertEquals(locationString, shotDescriptionWithLocation.title(composeRule.activity.applicationContext))
    }
}