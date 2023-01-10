package de.leuchtetgruen.cameraman.api

import de.leuchtetgruen.cameraman.api.network_model.ApiTokenDto
import de.leuchtetgruen.cameraman.api.network_model.LoginObjectDto
import de.leuchtetgruen.cameraman.api.network_model.ShotDescriptionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ScriptWriterApi {
    @POST("/api/login_check")
    suspend fun login(@Body loginObject: LoginObjectDto) : Response<ApiTokenDto>

    @POST("/api/token/refresh")
    suspend fun refreshToken(@Query("refresh_token") refreshToken : String) : Response<ApiTokenDto>

    @GET("/api/shot_descriptions.json")
    suspend fun shotDescriptions() : Response<List<ShotDescriptionDto>>
}