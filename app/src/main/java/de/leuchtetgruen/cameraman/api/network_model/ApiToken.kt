package de.leuchtetgruen.cameraman.api.network_model

data class ApiToken(
    val token: String,
    val refresh_token: String
)