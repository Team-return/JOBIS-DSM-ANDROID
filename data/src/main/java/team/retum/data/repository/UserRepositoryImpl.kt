package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.UserDataSource
import team.retum.data.remote.request.LoginRequest
import team.retum.data.remote.request.toRequest
import team.retum.data.remote.response.SignInResponse
import team.retum.domain.param.CheckStudentExistsParam
import team.retum.domain.param.LoginParam
import team.retum.domain.param.SendVerificationCodeParam
import team.retum.domain.param.SignUpParam
import team.retum.domain.param.VerifyEmailParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun postLogin(param: LoginParam) {

        val response = userDataSource.postLogin(
            loginRequest = param.toRequest(),
        )

        userDataSource.setUserInfo(
            signInResponse = SignInResponse(
                accessToken = response.accessToken,
                accessExpiresAt = response.accessExpiresAt,
                refreshToken = response.refreshToken,
                refreshTokenExpiresAt = response.refreshTokenExpiresAt,
                authority = response.authority,
            )
        )
    }

    override suspend fun sendVerificationCode(
        sendVerificationCodeParam: SendVerificationCodeParam,
    ) {
        userDataSource.sendVerificationCode(
            sendVerificationCodeRequest = sendVerificationCodeParam.toRequest(),
        )
    }

    override suspend fun checkStudentExists(
        checkStudentExistsParam: CheckStudentExistsParam,
    ) {
        userDataSource.checkStudentExists(
            gcn = checkStudentExistsParam.gcn,
            name = checkStudentExistsParam.name,
        )
    }

    override suspend fun signUp(
        signUpParam: SignUpParam,
    ) {
        userDataSource.signUp(
            signUpRequest = signUpParam.toRequest(),
        )
    }

    override suspend fun verifyEmail(
        verifyEmailParam: VerifyEmailParam,
    ) {
        userDataSource.verifyEmail(
            email = verifyEmailParam.email,
            authCode = verifyEmailParam.authCode,
        )
    }

    private fun LoginParam.toRequest(): LoginRequest =
        LoginRequest(
            accountId = accountId,
            password = password,
        )
}