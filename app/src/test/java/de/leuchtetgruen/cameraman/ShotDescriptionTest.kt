package de.leuchtetgruen.cameraman

import de.leuchtetgruen.cameraman.businessobjects.ShotDescription
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
}