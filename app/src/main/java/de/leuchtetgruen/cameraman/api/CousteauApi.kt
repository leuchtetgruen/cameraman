package de.leuchtetgruen.cameraman.api

import de.leuchtetgruen.cameraman.api.network_model.ApiTokenDto
import de.leuchtetgruen.cameraman.api.network_model.LoginObjectDto
import de.leuchtetgruen.cameraman.api.network_model.ShotDescriptionDto
import de.leuchtetgruen.cameraman.api.network_model.ShotDoneStateObjectDto
import retrofit2.Response
import retrofit2.http.*

interface CousteauApi {
    @POST("/api/login_check")
    suspend fun login(@Body loginObject: LoginObjectDto) : Response<ApiTokenDto>

    @PATCH("/api/shot_descriptions/{id}")
    @Headers("Content-Type: application/merge-patch+json")
    suspend fun changeDoneState(@Path("id") id : Int, @Body shotDoneStateObject: ShotDoneStateObjectDto) : Response<ShotDescriptionDto>

    @POST("/api/token/refresh")
    suspend fun refreshToken(@Query("refresh_token") refreshToken : String) : Response<ApiTokenDto>

    @GET("/api/shot_descriptions.json")
    suspend fun shotDescriptions() : Response<List<ShotDescriptionDto>>
}