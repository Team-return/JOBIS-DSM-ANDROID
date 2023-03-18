package team.retum.domain.usecase

import team.retum.domain.param.LoginParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<Unit, LoginParam>() {
    override suspend fun execute(data: Unit): LoginParam =
        userRepository.fetchUserInfo()
}