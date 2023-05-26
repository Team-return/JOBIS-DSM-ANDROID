package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.request.LoginRequest
import team.retum.data.remote.request.SendVerificationCodeRequest
import team.retum.data.remote.request.SignUpRequest
import team.retum.data.remote.response.SignInResponse

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): SignInResponse

    suspend fun sendVerificationCode(sendVerificationCodeRequest: SendVerificationCodeRequest)

    suspend fun checkStudentExists(
        gcn: Int,
        name: String,
    )

    suspend fun verifyEmail(
        email: String,
        authCode: String,
    )

    suspend fun signUp(
        signUpRequest: SignUpRequest,
    )

    suspend fun setUserInfo(signInResponse: SignInResponse)
}