package de.leuchtetgruen.cameraman.domain.model

import de.leuchtetgruen.cameraman.R

data class ShotDescription(
    val id: Int,
    val description: String,
    var done: Boolean,
    val lat: Double,
    val lng: Double,
    val imageUrl : String?,
    val linkedMediaUrl: String?,
) {

    fun hasLocation() : Boolean {
        return (lat != 0.0) && (lng != 0.0)
    }

    fun hasLinkedMedia() : Boolean {
        return linkedMediaUrl != null
    }

    fun titleStringId() : Int {
        if (hasLocation()) {
            return R.string.on_spot_shot
        }

        if (hasLinkedMedia()) {
            return R.string.linked_medium
        }

        return R.string.shot
    }
}