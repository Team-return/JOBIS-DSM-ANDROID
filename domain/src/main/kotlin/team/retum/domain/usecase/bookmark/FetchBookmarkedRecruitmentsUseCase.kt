package team.retum.domain.usecase.bookmark

import team.retum.domain.repository.BookmarkRepository
import javax.inject.Inject

class FetchBookmarkedRecruitmentsUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {
    suspend operator fun invoke() = runCatching {
        bookmarkRepository.fetchBookmarkedRecruitments()
    }
}
