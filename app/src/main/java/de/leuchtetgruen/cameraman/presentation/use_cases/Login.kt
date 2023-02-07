package de.leuchtetgruen.cameraman.presentation.use_cases

import de.leuchtetgruen.cameraman.api.CousteauApi
import de.leuchtetgruen.cameraman.api.RuntimeTokenStore
import de.leuchtetgruen.cameraman.api.network_model.LoginObjectDto
import de.leuchtetgruen.cameraman.businessobjects.TokenProvider
import javax.inject.Inject

class Login @Inject constructor(val api : CousteauApi, val runtimeTokenStore: RuntimeTokenStore, val tokenProvider: TokenProvider) {

    suspend operator fun invoke(username: String, password: String) : Boolean {
        val apiTokenResponse =  api.login(LoginObjectDto(username, password))

        if (apiTokenResponse.errorBody() != null) {
            return false
        }

        runtimeTokenStore.token = apiTokenResponse.body()?.token!!

        val refreshToken = apiTokenResponse.body()?.refresh_token!!

        runtimeTokenStore.refreshToken = refreshToken
        tokenProvider.setRefreshToken(refreshToken)

        return true
    }
}