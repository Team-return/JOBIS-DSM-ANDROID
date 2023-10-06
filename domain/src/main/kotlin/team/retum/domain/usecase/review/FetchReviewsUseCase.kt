package team.retum.domain.usecase.review

import team.retum.domain.repository.ReviewRepository
import javax.inject.Inject

class FetchReviewsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
) {
    suspend operator fun invoke(
        companyId: Long,
    ) = runCatching {
        reviewRepository.fetchReviews(
            companyId = companyId,
        )
    }
}