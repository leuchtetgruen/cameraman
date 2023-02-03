package de.leuchtetgruen.cameraman.mocks.repository

import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.domain.repository.ShotDescriptionRepository

object FakeShotDescriptionRepository : ShotDescriptionRepository {

    var shots : MutableList<ShotDescription> = mutableListOf()

    override suspend fun loadShotDescriptions(): List<ShotDescription> {

        return shots
    }

    override suspend fun changeDoneState(shotDescription: ShotDescription, done: Boolean) {
        shots.find { it.id == shotDescription.id }?.done = done
    }

    override suspend fun getShotWithId(id: Int): ShotDescription? {
        return shots.find { it.id == id }
    }
}