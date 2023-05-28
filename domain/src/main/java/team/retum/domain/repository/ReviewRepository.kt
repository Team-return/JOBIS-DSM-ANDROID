package team.retum.domain.repository

import team.retum.domain.entity.review.ReviewsEntity

interface ReviewRepository {
    suspend fun fetchReviews(
        companyId: Long,
    ): ReviewsEntity
}