package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.UserDataSource
import team.retum.data.remote.request.LoginRequest
import team.retum.domain.param.LoginParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override suspend fun postLogin(param: LoginParam) {
        if(param.isAutoLogin){
            userDataSource.setUserInfo(param)
        }
        userDataSource.postLogin(
            loginRequest = param.toRequest(),
        )
    }

    override suspend fun fetchUserInfo(): LoginParam =
        userDataSource.fetchUserInfo()

    private fun LoginParam.toRequest(): LoginRequest =
        LoginRequest(
            accountId = accountId,
            password = password,
        )
}