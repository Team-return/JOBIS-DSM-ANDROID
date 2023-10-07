package team.retum.data.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import team.retum.data.remote.request.user.SignInRequest
import team.retum.data.remote.response.user.SignInResponse
import team.retum.data.remote.url.JobisUrl

interface UserApi {
    @POST(JobisUrl.User.login)
    suspend fun postLogin(
        @Body signInRequest: SignInRequest,
    ): SignInResponse
}
