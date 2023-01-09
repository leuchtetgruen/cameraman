package de.leuchtetgruen.cameraman.businessobjects

import android.content.Context

object TokenProvider {
    const val REFRESH_TOKEN_KEY = "refreshToken"
    const val SHARED_PREFS_KEY = "cameraManSharedPrefs"

    fun hasRefreshToken(context : Context) : Boolean {
        return context.getSharedPreferences(SHARED_PREFS_KEY, 0).contains(REFRESH_TOKEN_KEY)
    }

    fun needsLogin(context: Context) : Boolean {
        return !hasRefreshToken(context)
    }

    fun getRefreshToken(context: Context) : String {
        return context.getSharedPreferences(SHARED_PREFS_KEY, 0).getString(REFRESH_TOKEN_KEY, "shouldNeverHappen")!!
    }

    fun setRefreshToken(context: Context, refreshToken: String) {
        context.getSharedPreferences(SHARED_PREFS_KEY, 0).edit().putString(REFRESH_TOKEN_KEY, refreshToken).commit()
    }
}