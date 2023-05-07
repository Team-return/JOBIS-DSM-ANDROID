package team.retum.domain.repository

import team.retum.domain.param.LoginParam
import team.retum.domain.param.SendVerificationCodeParam


interface UserRepository {
    suspend fun postLogin(param: LoginParam)
    suspend fun sendVerificationCode(sendVerificationCodeParam: SendVerificationCodeParam)
    suspend fun fetchUserInfo(): LoginParam
}