package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.UserApi
import team.retum.data.remote.datasource.declaration.UserDataSource
import team.retum.data.remote.request.user.SignInRequest
import team.retum.data.remote.request.user.SendVerificationCodeRequest
import team.retum.data.remote.request.user.SignUpRequest
import team.retum.data.remote.response.user.SignInResponse
import team.retum.data.remote.response.user.SignUpResponse
import team.retum.data.storage.UserDataStorage
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
    private val userDataStorage: UserDataStorage,
) : UserDataSource {
    override suspend fun postLogin(
        signInRequest: SignInRequest,
    ) = HttpHandler<SignInResponse>()
        .httpRequest {
            userApi.postLogin(
                signInRequest = signInRequest,
            )
        }.sendRequest()

    override suspend fun sendVerificationCode(
        sendVerificationCodeRequest: SendVerificationCodeRequest,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.sendVerificationCode(
                sendVerificationCodeRequest = sendVerificationCodeRequest,
            )
        }.sendRequest()

    override suspend fun checkStudentExists(
        gcn: Int,
        name: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.checkStudentExists(
                gcn = gcn,
                name = name,
            )
        }.sendRequest()

    override suspend fun verifyEmail(
        email: String,
        authCode: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.verifyEmail(
                email = email,
                authCode = authCode,
            )
        }.sendRequest()

    override suspend fun signUp(
        signUpRequest: SignUpRequest,
    ) = HttpHandler<SignUpResponse>()
        .httpRequest {
            userApi.signUp(
                signUpRequest = signUpRequest,
            )
        }.sendRequest()

    override suspend fun setUserInfo(
        signInResponse: SignInResponse,
    ) {
        userDataStorage.setUserInfo(
            accessToken = signInResponse.accessToken,
            accessTokenExpiresAt = signInResponse.accessExpiresAt,
            refreshToken = signInResponse.refreshToken,
            refreshTokenExpiresAt = signInResponse.refreshTokenExpiresAt,
            authority = signInResponse.authority,
        )
    }

    override suspend fun setAutoSignInOption(
        autoSignInOption: Boolean,
    ) {
        userDataStorage.setAutoSignInOption(
            autoSignInOption = autoSignInOption,
        )
    }

    override suspend fun fetchAutoSignInOption(): Boolean {
        return userDataStorage.fetchAutoSignInOption()
    }
}