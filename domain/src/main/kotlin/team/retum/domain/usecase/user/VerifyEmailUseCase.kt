package team.retum.domain.usecase.user

import team.retum.domain.param.user.VerifyEmailParam
import team.retum.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(verifyEmailParam: VerifyEmailParam) = runCatching {
        authRepository.verifyEmail(verifyEmailParam = verifyEmailParam)
    }
}
