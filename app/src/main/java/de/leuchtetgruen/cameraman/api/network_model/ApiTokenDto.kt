package de.leuchtetgruen.cameraman.api.network_model

data class ApiTokenDto(
    val token: String,
    val refresh_token: String
)