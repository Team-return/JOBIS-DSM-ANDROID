package com.jobis.data.remote.datasource.declaration

import com.jobis.data.remote.request.LoginRequest
import com.jobis.data.remote.response.LoginResponse

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse
}