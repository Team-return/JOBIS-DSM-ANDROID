package com.jobis.data.remote.api

import com.jobis.data.remote.request.LoginRequest
import com.jobis.data.remote.response.LoginResponse
import com.jobis.data.remote.url.JobisUrl
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST(JobisUrl.USER.login)
    fun postLogin(
        @Body loginRequest: LoginRequest,
    ): LoginResponse
}