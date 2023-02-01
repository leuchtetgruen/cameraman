package de.leuchtetgruen.cameraman.presentation.use_cases

import android.content.Context
import de.leuchtetgruen.cameraman.api.CousteauApi
import de.leuchtetgruen.cameraman.api.RuntimeTokenStore
import de.leuchtetgruen.cameraman.api.network_model.LoginObjectDto
import de.leuchtetgruen.cameraman.businessobjects.TokenProvider
import javax.inject.Inject

class Login @Inject constructor(val api : CousteauApi, val runtimeTokenStore: RuntimeTokenStore) {

    suspend operator fun invoke(username : String, password: String, context: Context) : Boolean {
        val apiTokenResponse =  api.login(LoginObjectDto(username, password))

        if (apiTokenResponse.errorBody() != null) {
            return false
        }

        runtimeTokenStore.token = apiTokenResponse.body()?.token!!

        val refreshToken = apiTokenResponse.body()?.refresh_token!!

        runtimeTokenStore.refreshToken = refreshToken
        TokenProvider.setRefreshToken(context, refreshToken)

        return true
    }
}