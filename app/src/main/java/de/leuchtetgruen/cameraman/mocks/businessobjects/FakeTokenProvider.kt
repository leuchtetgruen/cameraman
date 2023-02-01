package de.leuchtetgruen.cameraman.mocks.businessobjects

import de.leuchtetgruen.cameraman.businessobjects.TokenProvider

object FakeTokenProvider : TokenProvider {
    var _refreshToken : String? = null

    override fun hasRefreshToken(): Boolean {
        return (_refreshToken != null)
    }

    override fun needsLogin(): Boolean {
        return !hasRefreshToken()
    }

    override fun getRefreshToken(): String {
        return _refreshToken.orEmpty()
    }

    override fun setRefreshToken(refreshToken: String) {
        _refreshToken = refreshToken
    }
}