package de.leuchtetgruen.cameraman.mocks.api

import de.leuchtetgruen.cameraman.api.CousteauApi
import de.leuchtetgruen.cameraman.api.network_model.*
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import retrofit2.Response

object FakeCousteauApi : CousteauApi {
    var loginShouldSucceed = true
    var shouldDelay = true

    var queryingShotDescriptionsShouldSucceed = true
    var shotDescriptionDtos = listOf<ShotDescriptionDto>()

    var queryingSourcesShouldSucceed = true
    var sourceDtos = listOf<SourceDto>()

    override suspend fun login(loginObject: LoginObjectDto): Response<ApiTokenDto> {
        if (shouldDelay) delay(1000)
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
        if (shouldDelay) delay(1000)
        val successObject = ApiTokenDto("foobar", "foobar")
        return Response.success(successObject)
    }

    override suspend fun shotDescriptions(): Response<List<ShotDescriptionDto>> {
        if (shouldDelay) delay(1000)
        val errorBody = "{\"code\":401,\"message\":\"Expired JWT token.\"}"
        if (queryingShotDescriptionsShouldSucceed) {
            return Response.success(shotDescriptionDtos)
        }
        else {
            return Response.error(401, ResponseBody.create(null, errorBody))
        }
    }

    override suspend fun sources(): Response<List<SourceDto>> {
        if (shouldDelay) delay(1000)
        val errorBody = "{\"code\":401,\"message\":\"Expired JWT token.\"}"
        if (queryingSourcesShouldSucceed) {
            return Response.success(sourceDtos)
        }
        else {
            return Response.error(401, ResponseBody.create(null, errorBody))
        }
    }
}