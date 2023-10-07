package team.retum.data.remote.datasource.auth

import team.retum.data.remote.request.user.SendVerificationCodeRequest

interface AuthDataSource {
    suspend fun sendVerificationCode(sendVerificationCodeRequest: SendVerificationCodeRequest)

    suspend fun verifyEmail(
        email: String,
        authCode: String,
    )
}
