package com.jobis.domain.repository

import com.jobis.domain.param.LoginParam

interface UserRepository {
    suspend fun postLogin(param: LoginParam)
    suspend fun fetchUserInfo(): LoginParam
}