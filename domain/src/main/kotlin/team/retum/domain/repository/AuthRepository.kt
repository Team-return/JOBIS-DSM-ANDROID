package team.retum.domain.repository

import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.VerifyEmailParam

interface AuthRepository {
    suspend fun sendVerificationCode(sendVerificationCodeParam: SendVerificationCodeParam)

    suspend fun verifyEmail(verifyEmailParam: VerifyEmailParam)
}