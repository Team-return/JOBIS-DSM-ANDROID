package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.UserApi
import team.retum.data.remote.datasource.declaration.UserDataSource
import team.retum.data.remote.request.LoginRequest
import team.retum.data.remote.request.SendVerificationCodeRequest
import team.retum.data.remote.response.LoginResponse
import team.retum.data.storage.UserDataStorage
import team.retum.data.util.HttpHandler
import team.retum.domain.param.LoginParam
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
    private val userDataStorage: UserDataStorage,
) : UserDataSource {
    override suspend fun postLogin(
        loginRequest: LoginRequest,
    ) = HttpHandler<LoginResponse>()
        .httpRequest {
            userApi.postLogin(
                loginRequest = loginRequest,
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

    override suspend fun setUserInfo(
        loginParam: LoginParam,
    ) {
        userDataStorage.setUserInfo(
            accountId = loginParam.accountId,
            password = loginParam.password,
        )
    }

    override suspend fun fetchUserInfo(): LoginParam =
        userDataStorage.run {
            LoginParam(
                accountId = fetchUserId(),
                password = fetchPassword(),
                isAutoLogin = true,
            )
        }
}