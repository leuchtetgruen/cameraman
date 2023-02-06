package de.leuchtetgruen.cameraman.domain.repository.interfaces

import de.leuchtetgruen.cameraman.domain.model.ShotDescription

interface ShotDescriptionRepository {
    suspend fun loadShotDescriptions() : List<ShotDescription>
    suspend fun changeDoneState(shotDescription: ShotDescription, done: Boolean)
    suspend fun getShotWithId(id: Int) : ShotDescription?
}