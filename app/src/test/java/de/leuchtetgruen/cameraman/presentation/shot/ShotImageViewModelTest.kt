package de.leuchtetgruen.cameraman.presentation.shot

import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ShotImageViewModelTest {

    @Test
    fun `finds no image url to show if there is no image in shot description`() {
        val shotDescription = ShotDescription(0, "", false, 0.0, 0.0, null, null)

        val vm = ShotImageViewModel(shotDescription)

        assertNull(vm.imageUrlFromShotDescription())
    }

    @Test
    fun `finds no image url to show if there is only a linked medium but it's not a jpg`() {
        val shotDescription = ShotDescription(0, "", false, 0.0, 0.0, null, "http://test.de/test.xml")

        val vm = ShotImageViewModel(shotDescription)

        assertNull(vm.imageUrlFromShotDescription())
    }

    @Test
    fun `finds image url from shot description if present`() {
        val imageUrl = "http://test.de/test.jpg"
        val shotDescription = ShotDescription(0, "", false, 0.0, 0.0, imageUrl, null)

        val vm = ShotImageViewModel(shotDescription)
        assertEquals(vm.imageUrlFromShotDescription(), imageUrl)
    }

    @Test
    fun `finds  image url to show if there is only a linked medium and it's a jpg`() {
        val linkedMediaUrl = "http://test.de/test.jpg"
        val shotDescription = ShotDescription(0, "", false, 0.0, 0.0, null, linkedMediaUrl)

        val vm = ShotImageViewModel(shotDescription)

        assertEquals(vm.imageUrlFromShotDescription(), linkedMediaUrl)
    }

    @Test
    fun `prefers imageurl over linkedMediaUrl`() {
        val imageUrl = "http://test.de/test.jpg"
        val linkedMediaUrl = "http://test.de/test2.jpg"
        val shotDescription = ShotDescription(0, "", false, 0.0, 0.0, imageUrl, linkedMediaUrl)

        val vm = ShotImageViewModel(shotDescription)

        assertEquals(vm.imageUrlFromShotDescription(), imageUrl)
    }
}