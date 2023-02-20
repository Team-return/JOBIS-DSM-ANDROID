package com.jobis.data.repository

import com.jobis.data.remote.datasource.declaration.UserDataSource
import com.jobis.data.remote.request.LoginRequest
import com.jobis.domain.param.LoginParam
import com.jobis.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun postLogin(param: LoginParam) {
        if(param.isAutoLogin){
            userDataSource.setUserInfo(param)
        }
        userDataSource.postLogin(
            loginRequest = param.toRequest(),
        )
    }

    override suspend fun fetchUserInfo(): LoginParam =
        userDataSource.fetchUserInfo()

    private fun LoginParam.toRequest(): LoginRequest =
        LoginRequest(
            accountId = accountId,
            password = password,
        )
}