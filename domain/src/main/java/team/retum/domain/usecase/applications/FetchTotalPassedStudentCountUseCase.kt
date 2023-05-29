package team.retum.domain.usecase.applications

import team.retum.domain.repository.ApplicationsRepository
import javax.inject.Inject

class FetchTotalPassedStudentCountUseCase @Inject constructor(
    private val applicationsRepository: ApplicationsRepository,
) {
    suspend operator fun invoke() = runCatching {
        applicationsRepository.fetchPassedStudentCount()
    }
}