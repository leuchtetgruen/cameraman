package de.leuchtetgruen.cameraman.data

import de.leuchtetgruen.cameraman.api.network_model.ShotDescriptionDto
import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.mocks.api.FakeCousteauApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ShotDescriptionRepositoryImplTest {

    private lateinit var shotDescriptionRepositoryImpl: ShotDescriptionRepositoryImpl

    @Before
    fun setup() {
        shotDescriptionRepositoryImpl = ShotDescriptionRepositoryImpl(FakeCousteauApi)
    }

    @Test
    fun `error querying descriptions leads to exception`() {
        var exceptionThrown = false

        FakeCousteauApi.queryingShotDescriptionsShouldSucceed = false
        runBlocking {
            try {
                shotDescriptionRepositoryImpl.loadShotDescriptions()
            } catch (e: Exception) {
                exceptionThrown = true
            }
        }
        assert(exceptionThrown)
    }

    @Test
    fun `no error querying descriptions leads to no exception`() {
        var exceptionThrown = false

        FakeCousteauApi.queryingShotDescriptionsShouldSucceed = true
        runBlocking {
            try {
                shotDescriptionRepositoryImpl.loadShotDescriptions()
            } catch (e: Exception) {
                exceptionThrown = true
            }
        }
        assertFalse(exceptionThrown)
    }

    @Test
    fun `empty shot list will be received correctly`() {
        val expected : List<ShotDescription> = listOf()
        FakeCousteauApi.queryingShotDescriptionsShouldSucceed = true
        FakeCousteauApi.shotDescriptionDtos = listOf()

        var received: List<ShotDescription>? = null
        runBlocking {
            received = shotDescriptionRepositoryImpl.loadShotDescriptions()
        }

        assertNotNull(received)
        assertArrayEquals(received!!.toTypedArray(), expected.toTypedArray())
    }

    @Test
    fun `filled list will return correctly`() {
        val obj1 = ShotDescription(0, "descr", true, 0.0, 0.0, null, null)
        val obj2 = ShotDescription(1, "descr2", true, 10.0, 0.0, null, null)
        val obj3 = ShotDescription(2, "desc3r", true, 0.0, 10.0, null, null)
        val dtoList = listOf<ShotDescriptionDto>(
            ShotDescriptionDto(obj1.id, obj1.description, obj1.done, obj1.lat, obj1.lng, listOf(), "ref", obj1.imageUrl, obj1.linkedMediaUrl),
            ShotDescriptionDto(obj2.id, obj2.description, obj2.done, obj2.lat, obj2.lng, listOf(), "ref", obj2.imageUrl, obj2.linkedMediaUrl),
            ShotDescriptionDto(obj3.id, obj3.description, obj3.done, obj3.lat, obj3.lng, listOf(), "ref", obj3.imageUrl, obj3.linkedMediaUrl),
        )
        val objList = listOf<ShotDescription>(
            obj1,
            obj2,
            obj3
        )

        FakeCousteauApi.queryingShotDescriptionsShouldSucceed = true
        FakeCousteauApi.shotDescriptionDtos = dtoList

        var received: List<ShotDescription>? = null
        runBlocking {
            received = shotDescriptionRepositoryImpl.loadShotDescriptions()
        }

        assertNotNull(received)
        assertArrayEquals(received!!.toTypedArray(), objList.toTypedArray())
    }

}