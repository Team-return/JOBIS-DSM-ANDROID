package team.retum.domain.usecase.review

import team.retum.domain.repository.ReviewRepository
import javax.inject.Inject

class FetchReviewDetailsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
) {
    suspend operator fun invoke(reviewId: String) = runCatching {
        reviewRepository.fetchReviewDetails(reviewId = reviewId)
    }
}
