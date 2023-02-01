package de.leuchtetgruen.cameraman.businessobjects

interface TokenProvider {
    fun hasRefreshToken() : Boolean
    fun needsLogin() : Boolean
    fun getRefreshToken() : String
    fun setRefreshToken(refreshToken: String)
}