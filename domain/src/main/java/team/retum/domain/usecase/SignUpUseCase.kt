package team.retum.domain.usecase

import team.retum.domain.param.SignUpParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        signUpParam: SignUpParam,
    ) = kotlin.runCatching {
        userRepository.signUp(
            signUpParam = signUpParam,
        )
    }
}