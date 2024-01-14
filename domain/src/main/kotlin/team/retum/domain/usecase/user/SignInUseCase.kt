package team.retum.domain.usecase.user

import team.retum.domain.param.user.SignInParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(param: SignInParam) = runCatching {
        userRepository.postLogin(param = param)
    }
}
