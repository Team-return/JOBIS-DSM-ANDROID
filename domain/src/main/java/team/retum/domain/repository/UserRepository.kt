package team.retum.domain.repository

import team.retum.domain.param.LoginParam


interface UserRepository {
    suspend fun postLogin(param: LoginParam)
    suspend fun fetchUserInfo(): LoginParam
}