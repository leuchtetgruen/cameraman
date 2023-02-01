package de.leuchtetgruen.cameraman.businessobjects

import android.content.Context

class TokenProviderImpl(val context: Context) : TokenProvider {
    val REFRESH_TOKEN_KEY = "refreshToken"
    val SHARED_PREFS_KEY = "cameraManSharedPrefs"

    override fun hasRefreshToken() : Boolean {
        return context.getSharedPreferences(SHARED_PREFS_KEY, 0).contains(REFRESH_TOKEN_KEY)
    }

    override fun needsLogin() : Boolean {
        return !hasRefreshToken()
    }

    override fun getRefreshToken() : String {
        return context.getSharedPreferences(SHARED_PREFS_KEY, 0).getString(REFRESH_TOKEN_KEY, "shouldNeverHappen")!!
    }

    override fun setRefreshToken(refreshToken: String) {
        context.getSharedPreferences(SHARED_PREFS_KEY, 0).edit().putString(REFRESH_TOKEN_KEY, refreshToken).commit()
    }
}