package de.leuchtetgruen.cameraman.businessobjects

object CurrentUser {
    var username : String = ""
    var token : String? = null

    fun isLoggedIn() : Boolean {
        return (!token.isNullOrBlank())
    }
}