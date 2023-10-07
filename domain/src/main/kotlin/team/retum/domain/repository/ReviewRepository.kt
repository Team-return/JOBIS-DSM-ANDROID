package team.retum.domain.repository

import team.retum.domain.entity.review.ReviewDetailsEntity
import team.retum.domain.entity.review.ReviewsEntity
import team.retum.domain.param.review.PostReviewParam

interface ReviewRepository {
    suspend fun fetchReviews(
        companyId: Long,
    ): ReviewsEntity

    suspend fun fetchReviewDetails(
        reviewId: String,
    ): ReviewDetailsEntity

    suspend fun postReview(postReviewParam: PostReviewParam)
}
