package de.leuchtetgruen.cameraman.presentation.shot

import androidx.lifecycle.ViewModel
import de.leuchtetgruen.cameraman.domain.model.ShotDescription

class ShotImageViewModel(val shotDescription: ShotDescription?) : ViewModel() {

    fun imageUrlFromShotDescription() : String? {
        if (shotDescription == null) return null

        if (shotDescription.imageUrl != null) return shotDescription.imageUrl

        if ((shotDescription.linkedMediaUrl != null) && shotDescription.linkedMediaUrl.lowercase().endsWith(".jpg")) return shotDescription.linkedMediaUrl

        return null
    }
}