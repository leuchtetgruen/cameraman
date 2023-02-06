package de.leuchtetgruen.cameraman.domain.repository.interfaces

import de.leuchtetgruen.cameraman.api.network_model.SourceDto
import de.leuchtetgruen.cameraman.domain.model.Source
import de.leuchtetgruen.cameraman.mocks.api.FakeCousteauApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SourcesRepositoryImplTest {
    private lateinit var sourcesRepositoryImpl: SourcesRepositoryImpl

    @Before
    fun setup() {
        sourcesRepositoryImpl = SourcesRepositoryImpl(FakeCousteauApi)
    }

    @Test
    fun `error querying descriptions leads to exception`() {
        var exceptionThrown = false

        FakeCousteauApi.queryingSourcesShouldSucceed = false
        runBlocking {
            try {
                sourcesRepositoryImpl.loadSources()
            } catch (e: Exception) {
                exceptionThrown = true
            }
        }
        assert(exceptionThrown)
    }

    @Test
    fun `no error querying descriptions leads to no exception`() {
        var exceptionThrown = false

        FakeCousteauApi.queryingSourcesShouldSucceed = true
        runBlocking {
            try {
                sourcesRepositoryImpl.loadSources()
            } catch (e: Exception) {
                exceptionThrown = true
            }
        }
        Assert.assertFalse(exceptionThrown)
    }

    @Test
    fun `empty sources list will be received correctly`() {
        val expected : List<Source> = listOf()
        FakeCousteauApi.queryingSourcesShouldSucceed = true
        FakeCousteauApi.sourceDtos = listOf()

        var received: List<Source>?
        runBlocking {
            received = sourcesRepositoryImpl.loadSources()
        }

        Assert.assertNotNull(received)
        Assert.assertArrayEquals(received!!.toTypedArray(), expected.toTypedArray())
    }

    @Test
    fun `filled list will return correctly`() {
        val obj1 = Source(0, "my title", "https://de.wikipedia.org/wiki/Test1")
        val obj2 = Source(1, "my title", "https://de.wikipedia.org/wiki/Test2")
        val obj3 = Source(2, "my title", "https://de.wikipedia.org/wiki/Test3")
        val dtoList = listOf<SourceDto>(
            SourceDto(obj1.id, obj1.title, obj1.url, "", null),
            SourceDto(obj2.id, obj2.title, obj2.url, "", null),
            SourceDto(obj3.id, obj3.title, obj3.url, "", null),
        )
        val objList = listOf<Source>(
            obj1,
            obj2,
            obj3
        )

        FakeCousteauApi.queryingSourcesShouldSucceed = true
        FakeCousteauApi.sourceDtos= dtoList

        var received: List<Source>?
        runBlocking {
            received = sourcesRepositoryImpl.loadSources()
        }

        Assert.assertNotNull(received)
        Assert.assertArrayEquals(received!!.toTypedArray(), objList.toTypedArray())
    }

}