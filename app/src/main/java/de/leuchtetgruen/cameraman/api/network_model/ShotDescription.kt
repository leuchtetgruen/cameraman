package de.leuchtetgruen.cameraman.api.network_model

data class ShotDescription(
    val description: String,
    val done: Boolean,
    val lat: Double,
    val lng: Double,
    val paragraphIris: List<String>,
    val ref: String
)