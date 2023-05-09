package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.request.LoginRequest
import team.retum.data.remote.request.SendVerificationCodeRequest
import team.retum.data.remote.response.LoginResponse
import team.retum.domain.param.LoginParam

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse

    suspend fun sendVerificationCode(sendVerificationCodeRequest: SendVerificationCodeRequest)

    suspend fun checkStudentExists(
        gcn: Int,
        name: String,
    )

    suspend fun verifyEmail(
        email: String,
        authCode: String,
    )

    suspend fun setUserInfo(loginParam: LoginParam)

    suspend fun fetchUserInfo(): LoginParam
}