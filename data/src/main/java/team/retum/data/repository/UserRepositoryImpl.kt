package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.UserDataSource
import team.retum.data.remote.request.LoginRequest
import team.retum.data.remote.request.toRequest
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
        if (param.isAutoLogin) {
            userDataSource.setUserInfo(param)
        }
        userDataSource.postLogin(
            loginRequest = param.toRequest(),
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

    override suspend fun fetchUserInfo(): LoginParam =
        userDataSource.fetchUserInfo()

    private fun LoginParam.toRequest(): LoginRequest =
        LoginRequest(
            accountId = accountId,
            password = password,
        )
}