package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.request.user.SignInRequest
import team.retum.data.remote.response.user.SignInResponse

interface UserDataSource {
    suspend fun postLogin(signInRequest: SignInRequest): SignInResponse

    suspend fun setUserInfo(signInResponse: SignInResponse)

    suspend fun setAutoSignInOption(autoSignInOption: Boolean)

    suspend fun fetchAutoSignInOption(): Boolean

    suspend fun signOut()
}