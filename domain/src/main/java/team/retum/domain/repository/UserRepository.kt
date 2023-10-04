package team.retum.domain.repository

import team.retum.domain.param.user.SignInParam

interface UserRepository {
    suspend fun postLogin(
        param: SignInParam,
    )

    suspend fun fetchAutoSignInOption(): Boolean

    suspend fun signOut()
}