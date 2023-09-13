package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.UserDataSource
import team.retum.data.remote.request.user.SignInRequest
import team.retum.data.remote.request.user.toRequest
import team.retum.data.remote.response.user.SignInResponse
import team.retum.domain.param.user.CheckStudentExistsParam
import team.retum.domain.param.user.SignInParam
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.SignUpParam
import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun postLogin(param: SignInParam) {

        val response = userDataSource.postLogin(
            signInRequest = param.toRequest(),
        )

        userDataSource.run {
            setUserInfo(
                signInResponse = SignInResponse(
                    accessToken = response.accessToken,
                    accessExpiresAt = response.accessExpiresAt,
                    refreshToken = response.refreshToken,
                    refreshExpiresAt = response.refreshExpiresAt,
                    authority = response.authority,
                )
            )

            setAutoSignInOption(
                autoSignInOption = param.isAutoLogin,
            )
        }
    }

    override suspend fun sendVerificationCode(
        sendVerificationCodeParam: SendVerificationCodeParam,
    ) {
        userDataSource.sendVerificationCode(
            sendVerificationCodeRequest = sendVerificationCodeParam.toRequest(),
        )
    }

    override suspend fun fetchAutoSignInOption(): Boolean {
        return userDataSource.fetchAutoSignInOption()
    }

    override suspend fun verifyEmail(
        verifyEmailParam: VerifyEmailParam,
    ) {
        userDataSource.verifyEmail(
            email = verifyEmailParam.email,
            authCode = verifyEmailParam.authCode,
        )
    }

    private fun SignInParam.toRequest(): SignInRequest =
        SignInRequest(
            accountId = accountId,
            password = password,
        )

    override suspend fun signOut() {
        userDataSource.signOut()
    }
}