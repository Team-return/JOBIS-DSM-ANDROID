package team.retum.domain.usecase.recruitment

import team.retum.domain.repository.RecruitmentRepository
import javax.inject.Inject

class FetchRecruitmentDetailsUseCase @Inject constructor(
    private val recruitmentRepository: RecruitmentRepository,
) {
    suspend operator fun invoke(recruitmentId: Long?) = runCatching {
        recruitmentRepository.fetchRecruitmentDetails(
            recruitmentId = recruitmentId ?: throw NullPointerException(),
        )
    }
}
