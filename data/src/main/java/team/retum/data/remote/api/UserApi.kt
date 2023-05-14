package team.retum.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import team.retum.data.remote.request.LoginRequest
import team.retum.data.remote.request.SendVerificationCodeRequest
import team.retum.data.remote.request.SignUpRequest
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

    @GET(JobisUrl.Student.exists)
    suspend fun checkStudentExists(
        @Query("gcn") gcn: Int,
        @Query("name") name: String,
    )

    @PATCH(JobisUrl.Auth.code)
    suspend fun verifyEmail(
        @Query("email") email: String,
        @Query("auth-code") authCode: String,
    ): Response<Void>

    @POST(JobisUrl.student)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest,
    )
}