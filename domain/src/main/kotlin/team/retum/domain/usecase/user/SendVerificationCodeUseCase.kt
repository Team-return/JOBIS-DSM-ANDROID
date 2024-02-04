package team.retum.domain.usecase.user

import team.retum.domain.param.user.SendVerificationCodeParam
import team.retum.domain.repository.AuthRepository
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(sendVerificationCodeParam: SendVerificationCodeParam) = runCatching {
        authRepository.sendVerificationCode(sendVerificationCodeParam = sendVerificationCodeParam)
    }
}
