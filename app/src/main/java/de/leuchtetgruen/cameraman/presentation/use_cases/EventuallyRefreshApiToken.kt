package de.leuchtetgruen.cameraman.presentation.use_cases

import de.leuchtetgruen.cameraman.api.CousteauApi
import de.leuchtetgruen.cameraman.api.RuntimeTokenStore
import javax.inject.Inject

class EventuallyRefreshApiToken @Inject constructor(val api: CousteauApi, val runtimeTokenStore: RuntimeTokenStore) {

    suspend operator fun invoke(refreshToken : String) {
        if (runtimeTokenStore.hasToken()) {
            return
        }

        runtimeTokenStore.refreshToken = refreshToken

        val apiTokenResponse =  api.refreshToken(refreshToken)

        val errorBody = apiTokenResponse.errorBody()
        if (errorBody != null) {
            throw  Exception("Error while refreshing token")
        }


        runtimeTokenStore.token = apiTokenResponse.body()?.token
        runtimeTokenStore.refreshToken = apiTokenResponse.body()?.refresh_token
    }
}