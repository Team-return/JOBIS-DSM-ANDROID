package team.retum.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import team.retum.data.remote.request.LoginRequest
import team.retum.data.remote.request.SendVerificationCodeRequest
import team.retum.data.remote.response.LoginResponse
import team.retum.data.remote.url.JobisUrl

interface UserApi {
    @POST(JobisUrl.User.login)
    suspend fun postLogin(
        @Body loginRequest: LoginRequest,
    ): LoginResponse

    @POST(JobisUrl.Auth.code)
    suspend fun sendVerificationCode(
        @Body sendVerificationCodeRequest: SendVerificationCodeRequest,
    ): Response<Void>
}