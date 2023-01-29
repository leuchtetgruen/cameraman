package de.leuchtetgruen.cameraman.businessobjects

data class ShotDescription(
    val id: Int,
    val description: String,
    val done: Boolean,
    val lat: Double,
    val lng: Double,
    val imageUrl : String?,
) {

    fun hasLocation() : Boolean {
        return (lat != 0.0) && (lng != 0.0)
    }
}