package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.request.user.SignInRequest
import team.retum.data.remote.request.user.SendVerificationCodeRequest
import team.retum.data.remote.request.user.SignUpRequest
import team.retum.data.remote.response.user.SignInResponse
import team.retum.data.remote.response.user.SignUpResponse

interface UserDataSource {
    suspend fun postLogin(signInRequest: SignInRequest): SignInResponse

    suspend fun sendVerificationCode(sendVerificationCodeRequest: SendVerificationCodeRequest)

    suspend fun verifyEmail(
        email: String,
        authCode: String,
    )

    suspend fun signUp(
        signUpRequest: SignUpRequest,
    ): SignUpResponse

    suspend fun setUserInfo(signInResponse: SignInResponse)

    suspend fun setAutoSignInOption(autoSignInOption: Boolean)

    suspend fun fetchAutoSignInOption(): Boolean

    suspend fun signOut()
}