package team.retum.domain.usecase

import team.retum.domain.param.SendVerificationCodeParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        sendVerificationCodeParam: SendVerificationCodeParam,
    ) = kotlin.runCatching {
        userRepository.sendVerificationCode(
            sendVerificationCodeParam = sendVerificationCodeParam
        )
    }
}
