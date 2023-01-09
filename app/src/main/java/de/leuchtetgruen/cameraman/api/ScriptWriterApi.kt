package de.leuchtetgruen.cameraman.api

import de.leuchtetgruen.cameraman.api.network_model.ApiToken
import de.leuchtetgruen.cameraman.api.network_model.LoginObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ScriptWriterApi {
    @POST("/api/login_check")
    suspend fun login(@Body loginObject: LoginObject) : Response<ApiToken>

    @POST("/api/token/refresh")
    suspend fun refreshToken(@Query("refresh_token") refreshToken : String) : Response<ApiToken>
}