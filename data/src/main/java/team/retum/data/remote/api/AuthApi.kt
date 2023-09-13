package team.retum.data.remote.api

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import team.retum.data.remote.request.user.SendVerificationCodeRequest
import team.retum.data.remote.response.user.SignInResponse
import team.retum.data.remote.url.JobisUrl

interface AuthApi {
    @PUT(JobisUrl.Auth.reissue)
    suspend fun tokenReissue(
        @Header("X-Refresh-Token") xRefreshToken: String,
    ): SignInResponse

    @POST(JobisUrl.Auth.code)
    suspend fun sendVerificationCode(
        @Body sendVerificationCodeRequest: SendVerificationCodeRequest,
    )

    @PATCH(JobisUrl.Auth.code)
    suspend fun verifyEmail(
        @Query("email") email: String,
        @Query("auth_code") authCode: String,
    )
}