package team.retum.domain.repository

import team.retum.domain.param.user.CheckStudentExistsParam
import team.retum.domain.param.user.SignInParam
import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.param.user.SignUpParam
import team.retum.domain.param.user.VerifyEmailParam

interface UserRepository {
    suspend fun postLogin(
        param: SignInParam,
    )

    suspend fun sendVerificationCode(
        sendVerificationCodeParam: SendVerificationCodeParam,
    )

    suspend fun checkStudentExists(
        checkStudentExistsParam: CheckStudentExistsParam,
    )

    suspend fun verifyEmail(
        verifyEmailParam: VerifyEmailParam,
    )

    suspend fun signUp(
        signUpParam: SignUpParam,
    )

    suspend fun fetchAutoSignInOption(): Boolean
}