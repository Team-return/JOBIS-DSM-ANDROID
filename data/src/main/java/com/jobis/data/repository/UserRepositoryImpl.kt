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
        userDataSource.postLogin(
            loginRequest = toRequest(param)
        )
    }

    private fun toRequest(
        param: LoginParam,
    ): LoginRequest =
        LoginRequest(
            accountId = param.accountId,
            password = param.password,
        )
}