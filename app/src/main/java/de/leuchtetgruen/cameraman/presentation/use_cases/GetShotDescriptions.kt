package de.leuchtetgruen.cameraman.presentation.use_cases

import de.leuchtetgruen.cameraman.domain.model.ShotDescription
import de.leuchtetgruen.cameraman.domain.repository.interfaces.ShotDescriptionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetShotDescriptions @Inject constructor(val shotDescriptionRepository: ShotDescriptionRepository) {
     suspend operator fun invoke(includeDoneShots : Boolean) : List<ShotDescription> {
        return shotDescriptionRepository.loadShotDescriptions().filter {
            it.hasLocation() && (includeDoneShots || !it.done)
        }
    }
}