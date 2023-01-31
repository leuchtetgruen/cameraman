package de.leuchtetgruen.cameraman.domain.model

import android.content.Context
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

    fun title(ctx : Context) : String {
        if (hasLocation()) {
            return ctx.getString(R.string.on_spot_shot)
        }

        if (hasLinkedMedia()) {
            return ctx.getString(R.string.linked_medium)
        }

        return ctx.getString(R.string.shot)
    }
}