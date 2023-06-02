package team.retum.domain.usecase.user

import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class FetchAutoSignInOptionUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = runCatching {
        userRepository.fetchAutoSignInOption()
    }
}