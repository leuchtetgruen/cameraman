package de.leuchtetgruen.cameraman.api

import de.leuchtetgruen.cameraman.api.network_model.ApiTokenDto
import de.leuchtetgruen.cameraman.api.network_model.LoginObjectDto
import de.leuchtetgruen.cameraman.api.network_model.ShotDescriptionDto
import de.leuchtetgruen.cameraman.api.network_model.ShotDoneStateObjectDto
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import retrofit2.Response

object FakeCousteauApi : CousteauApi {
    var loginShouldSucceed = true

    override suspend fun login(loginObject: LoginObjectDto): Response<ApiTokenDto> {
        delay(1000)
        val successObject = ApiTokenDto("foobar", "foobar")
        val errorBody = "{\"code\":401,\"message\":\"Invalid credentials.\"}"
        if (loginShouldSucceed) {
            return Response.success(successObject)
        }
        else {
            return Response.error(401, ResponseBody.create(null, errorBody))
        }
    }

    override suspend fun changeDoneState(
        id: Int,
        shotDoneStateObject: ShotDoneStateObjectDto
    ): Response<ShotDescriptionDto> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(refreshToken: String): Response<ApiTokenDto> {
        delay(1000)
        val successObject = ApiTokenDto("foobar", "foobar")
        return Response.success(successObject)
    }

    override suspend fun shotDescriptions(): Response<List<ShotDescriptionDto>> {
        TODO("Not yet implemented")
    }
}