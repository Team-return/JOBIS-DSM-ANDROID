package team.retum.data.remote.datasource.user

import team.retum.data.remote.api.UserApi
import team.retum.data.remote.request.user.SignInRequest
import team.retum.data.remote.response.user.SignInResponse
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

    override suspend fun setUserInfo(
        signInResponse: SignInResponse,
    ) {
        userDataStorage.setUserInfo(
            accessToken = signInResponse.accessToken,
            accessTokenExpiresAt = signInResponse.accessExpiresAt,
            refreshToken = signInResponse.refreshToken,
            refreshTokenExpiresAt = signInResponse.refreshExpiresAt,
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

    override suspend fun signOut() {
        userDataStorage.clearUserInformation()
    }
}
