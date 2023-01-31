package de.leuchtetgruen.cameraman.presentation.use_cases

import de.leuchtetgruen.cameraman.data.repository.FakeShotDescriptionRepository
import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetShotDescriptionsTest {

    private lateinit var getShotDescriptions: GetShotDescriptions
    private lateinit var shotsRepository: FakeShotDescriptionRepository

    @Before
    fun setUp() {
        shotsRepository = FakeShotDescriptionRepository()

        shotsRepository.shots.add(ShotDescription(1, "shot 1", false, 0.0, 0.0, null, null ))
        shotsRepository.shots.add(ShotDescription(2, "shot 2", true, 0.0, 0.0, null, null ))
        shotsRepository.shots.add(ShotDescription(3, "shot 3", false, 52.0, 13.0, null, null ))
        shotsRepository.shots.add(ShotDescription(4, "shot 4", true, 52.0, 13.0, "https://test.com/test.jpg", null ))
        shotsRepository.shots.add(ShotDescription(4, "shot 4", true, 0.0, 0.0, "https://test.com/test.jpg", "https://test.com/test.jpg" ))


        getShotDescriptions = GetShotDescriptions(shotsRepository)
    }

    @Test
    fun does_not_include_shots_without_location() {
        runBlocking {
            val shots = getShotDescriptions(true)
            Assert.assertEquals(shots.size, 2)
        }
    }

    @Test
    fun finds_the_right_amount_of_shots_open() {
        runBlocking {
            val shots = getShotDescriptions(false)
            Assert.assertEquals(shots.size, 1)
        }
    }
}