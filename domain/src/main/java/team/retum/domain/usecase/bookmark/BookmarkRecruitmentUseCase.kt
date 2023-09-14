package team.retum.domain.usecase.bookmark

import team.retum.domain.repository.RecruitmentRepository
import javax.inject.Inject

class BookmarkRecruitmentUseCase @Inject constructor(
    private val recruitmentRepository: RecruitmentRepository,
) {
    suspend operator fun invoke(recruitmentId: Long) = runCatching {
        recruitmentRepository.bookmarkRecruitment(recruitmentId)
    }
}