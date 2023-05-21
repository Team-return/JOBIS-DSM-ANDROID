package team.retum.domain.repository

import team.retum.domain.entity.UserApplyCompaniesEntity
import team.retum.domain.param.CheckStudentExistsParam
import team.retum.domain.param.LoginParam
import team.retum.domain.param.SendVerificationCodeParam
import team.retum.domain.param.SignUpParam
import team.retum.domain.param.VerifyEmailParam


interface UserRepository {
    suspend fun postLogin(
        param: LoginParam,
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

    suspend fun fetchUserApplyCompanies(): UserApplyCompaniesEntity

    suspend fun fetchUserInfo(): LoginParam
}