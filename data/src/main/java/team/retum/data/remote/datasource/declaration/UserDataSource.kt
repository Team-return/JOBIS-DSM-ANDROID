package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.request.user.SignInRequest
import team.retum.data.remote.request.user.SendVerificationCodeRequest
import team.retum.data.remote.request.user.SignUpRequest
import team.retum.data.remote.response.user.SignInResponse

interface UserDataSource {
    suspend fun postLogin(signInRequest: SignInRequest): SignInResponse

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

    suspend fun setAutoSignInOption(autoSignInOption: Boolean)

    suspend fun fetchAutoSignInOption(): Boolean
}