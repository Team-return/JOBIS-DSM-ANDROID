package team.retum.data.repository

import team.retum.data.remote.datasource.auth.AuthDataSource
import team.retum.data.remote.request.user.toRequest
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun sendVerificationCode(
        sendVerificationCodeParam: SendVerificationCodeParam,
    ) {
        authDataSource.sendVerificationCode(
            sendVerificationCodeRequest = sendVerificationCodeParam.toRequest(),
        )
    }

    override suspend fun verifyEmail(
        verifyEmailParam: VerifyEmailParam,
    ) {
        authDataSource.verifyEmail(
            email = verifyEmailParam.email,
            authCode = verifyEmailParam.authCode,
        )
    }
}
