package team.retum.domain.usecase.bookmark

import team.retum.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRecruitmentUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {
    suspend operator fun invoke(recruitmentId: Long) = runCatching {
        bookmarkRepository.bookmarkRecruitment(recruitmentId)
    }
}
