package team.retum.data.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import team.retum.data.remote.request.LoginRequest
import team.retum.data.remote.response.LoginResponse
import team.retum.data.remote.url.JobisUrl

interface UserApi {
    @POST(JobisUrl.USER.login)
    suspend fun postLogin(
        @Body loginRequest: LoginRequest,
    ): LoginResponse
}