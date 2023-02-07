package de.leuchtetgruen.cameraman

import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import org.junit.Assert
import org.junit.Test

class ShotDescriptionTest {

    @Test
    fun hasLocation_detects_no_location() {
        val shotDescription = ShotDescription(1, "description", false, 0.0, 0.0, null, null)

        Assert.assertEquals(false, shotDescription.hasLocation())
    }

    @Test
    fun hasLocation_detects_location() {
        val shotDescription = ShotDescription(1, "description", false, 52.0, 13.0, null, null)

        Assert.assertEquals(true, shotDescription.hasLocation())
    }

    @Test
    fun hasLocation_detects_no_linked_media() {
        val shotDescription = ShotDescription(1, "description", false, 52.0, 13.0, null, null)

        Assert.assertEquals(false, shotDescription.hasLinkedMedia())
    }

    @Test
    fun hasLocation_detects_linked_media() {
        val shotDescription = ShotDescription(1, "description", false, 52.0, 13.0, null, "http://test.de/test.jpg")

        Assert.assertEquals(true, shotDescription.hasLinkedMedia())
    }

    @Test
    fun title_is_right_for_objects_with_location() {
        val shotDescriptionWithLocation = ShotDescription(1, "", false, 13.0, 52.0, null, null)


        Assert.assertEquals(R.string.on_spot_shot, shotDescriptionWithLocation.titleStringId())
    }

    @Test
    fun title_is_right_for_object_without_location_but_with_linked_media() {
        val shotWithoutLocationButWithLinkedMedia = ShotDescription(1, "", false, 0.0, 0.0, null, "linked.media")

        Assert.assertEquals(R.string.linked_medium, shotWithoutLocationButWithLinkedMedia.titleStringId())
    }

    @Test
    fun title_is_right_for_object_with_neither_location_nor_linked_media() {
        val shotWithoutLocationButWithLinkedMedia = ShotDescription(1, "", false, 0.0, 0.0, null, null)


        Assert.assertEquals(R.string.shot, shotWithoutLocationButWithLinkedMedia.titleStringId())
    }

    @Test
    fun title_is_right_for_objects_with_location_and_linked_media() {
        val shotDescriptionWithLocation = ShotDescription(1, "", false, 13.0, 52.0, null, "linked.medium")


        Assert.assertEquals(R.string.on_spot_shot, shotDescriptionWithLocation.titleStringId())
    }
}