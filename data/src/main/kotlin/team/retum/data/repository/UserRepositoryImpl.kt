package team.retum.data.repository

import team.retum.data.remote.datasource.user.UserDataSource
import team.retum.data.remote.request.user.SignInRequest
import team.retum.data.remote.response.user.SignInResponse
import team.retum.domain.param.user.SignInParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun postLogin(param: SignInParam) {
        val response = userDataSource.postLogin(
            signInRequest = param.toRequest(),
        )

        userDataSource.run {
            setUserInfo(
                signInResponse = SignInResponse(
                    accessToken = response.accessToken,
                    accessExpiresAt = response.accessExpiresAt,
                    refreshToken = response.refreshToken,
                    refreshExpiresAt = response.refreshExpiresAt,
                    authority = response.authority,
                ),
            )

            setAutoSignInOption(
                autoSignInOption = param.isAutoLogin,
            )
        }
    }

    override suspend fun fetchAutoSignInOption(): Boolean {
        return userDataSource.fetchAutoSignInOption()
    }

    private fun SignInParam.toRequest(): SignInRequest =
        SignInRequest(
            accountId = accountId,
            password = password,
        )

    override suspend fun signOut() {
        userDataSource.signOut()
    }
}
