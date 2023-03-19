package team.retum.domain.usecase

import team.retum.domain.param.LoginParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<LoginParam, Unit>() {
    override suspend fun execute(data: LoginParam) {
        userRepository.postLogin(data)
    }
}