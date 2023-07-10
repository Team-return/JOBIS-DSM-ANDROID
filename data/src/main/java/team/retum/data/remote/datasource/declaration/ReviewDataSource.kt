package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.review.FetchReviewDetailsResponse
import team.retum.data.remote.response.review.FetchReviewsResponse

interface ReviewDataSource {
    suspend fun fetchReviews(
        companyId: Long,
    ): FetchReviewsResponse

    suspend fun fetchReviewDetails(
        reviewId: String,
    ): FetchReviewDetailsResponse
}