package team.retum.domain.usecase.user

import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class VerifyEmailUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        verifyEmailParam: VerifyEmailParam,
    ) = kotlin.runCatching {
        userRepository.verifyEmail(
            verifyEmailParam = verifyEmailParam,
        )
    }
}