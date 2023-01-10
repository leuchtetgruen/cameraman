package de.leuchtetgruen.cameraman.businessobjects

data class ShotDescription(
    val description: String,
    val done: Boolean,
    val lat: Double,
    val lng: Double
) {
}