package de.leuchtetgruen.cameraman.api

data class RuntimeTokenStore(var token : String?, var refreshToken : String?) {
    fun hasToken() : Boolean = token != null

    fun hasRefreshToken() : Boolean = refreshToken != null
}