package team.retum.domain.usecase.review

import team.retum.domain.param.review.PostReviewParam
import team.retum.domain.repository.ReviewRepository
import javax.inject.Inject

class PostReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository,
) {
    suspend operator fun invoke(postReviewParam: PostReviewParam) = runCatching{
        reviewRepository.postReview(postReviewParam)
    }
}