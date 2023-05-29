package team.retum.domain.usecase.user

import team.retum.domain.param.user.SignInParam
import team.retum.domain.repository.UserRepository
import team.retum.domain.usecase.UseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<SignInParam, Unit>() {
    override suspend fun execute(data: SignInParam) {
        userRepository.postLogin(data)
    }
}