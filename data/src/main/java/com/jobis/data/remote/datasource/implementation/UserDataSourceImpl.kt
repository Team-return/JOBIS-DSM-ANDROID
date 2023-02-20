package com.jobis.data.remote.datasource.implementation

import com.jobis.data.remote.api.UserApi
import com.jobis.data.remote.datasource.declaration.UserDataSource
import com.jobis.data.remote.request.LoginRequest
import com.jobis.data.remote.response.LoginResponse
import com.jobis.data.storage.UserDataStorage
import com.jobis.data.util.HttpHandler
import com.jobis.domain.param.LoginParam
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
    private val userDataStorage: UserDataStorage,
) : UserDataSource {
    override suspend fun postLogin(loginRequest: LoginRequest) =
        HttpHandler<LoginResponse>()
            .httpRequest {
                userApi.postLogin(
                    loginRequest = loginRequest,
                )
            }.sendRequest()

    override suspend fun setUserInfo(loginParam: LoginParam) {
        userDataStorage.putUserData(
            accountId = loginParam.accountId,
            password = loginParam.password,
        )
    }
}