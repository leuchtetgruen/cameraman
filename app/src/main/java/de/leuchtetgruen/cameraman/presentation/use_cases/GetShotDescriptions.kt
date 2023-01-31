package de.leuchtetgruen.cameraman.presentation.use_cases

import de.leuchtetgruen.cameraman.businessobjects.ShotDescription
import de.leuchtetgruen.cameraman.data.ShotDescriptionRepository

class GetShotDescriptions(val shotDescriptionRepository: ShotDescriptionRepository) {
     suspend operator fun invoke(includeDoneShots : Boolean) : List<ShotDescription> {
        return shotDescriptionRepository.loadShotDescriptions().filter {
            it.hasLocation() && (includeDoneShots || !it.done)
        }
    }
}