package com.jobis.data.remote.datasource.implementation

import com.jobis.data.remote.api.UserApi
import com.jobis.data.remote.datasource.declaration.UserDataSource
import com.jobis.data.remote.request.LoginRequest
import com.jobis.data.remote.response.LoginResponse
import com.jobis.data.util.HttpHandler
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : UserDataSource {
    override suspend fun postLogin(loginRequest: LoginRequest) =
        HttpHandler<LoginResponse>()
            .httpRequest {
                userApi.postLogin(
                    loginRequest = loginRequest,
                )
            }.sendRequest()
}