package team.retum.data.remote.datasource.auth

import team.retum.data.remote.api.auth.AuthApi
import team.retum.data.remote.request.user.SendVerificationCodeRequest
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
): AuthDataSource {
    override suspend fun sendVerificationCode(
        sendVerificationCodeRequest: SendVerificationCodeRequest,
    ) = HttpHandler<Unit>()
        .httpRequest {
            authApi.sendVerificationCode(
                sendVerificationCodeRequest = sendVerificationCodeRequest,
            )
        }.sendRequest()

    override suspend fun verifyEmail(
        email: String,
        authCode: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            authApi.verifyEmail(
                email = email,
                authCode = authCode,
            )
        }.sendRequest()
}