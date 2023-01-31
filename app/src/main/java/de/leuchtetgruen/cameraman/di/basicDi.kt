package de.leuchtetgruen.cameraman.di

import de.leuchtetgruen.cameraman.data.ShotDescriptionRepository
import de.leuchtetgruen.cameraman.presentation.use_cases.GetShotDescriptions

object BasicDI {
    val shotDescriptionRepository = ShotDescriptionRepository()

    val getShotDescriptions = GetShotDescriptions(shotDescriptionRepository)
}
