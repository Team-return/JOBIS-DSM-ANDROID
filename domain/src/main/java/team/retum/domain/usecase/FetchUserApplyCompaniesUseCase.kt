package team.retum.domain.usecase

import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class FetchUserApplyCompaniesUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = runCatching {
        userRepository.fetchUserApplyCompanies()
    }
}