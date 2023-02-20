package com.jobis.data.remote.datasource.declaration

import com.jobis.data.remote.request.LoginRequest
import com.jobis.data.remote.response.LoginResponse
import com.jobis.domain.param.LoginParam

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse
    suspend fun setUserInfo(loginParam: LoginParam)
}