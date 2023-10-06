package team.retum.data.remote.datasource.review

import team.retum.data.remote.request.review.PostReviewRequest
import team.retum.data.remote.response.review.FetchReviewDetailsResponse
import team.retum.data.remote.response.review.FetchReviewsResponse

interface ReviewDataSource {
    suspend fun fetchReviews(
        companyId: Long,
    ): FetchReviewsResponse

    suspend fun fetchReviewDetails(
        reviewId: String,
    ): FetchReviewDetailsResponse

    suspend fun postReview(postReviewRequest: PostReviewRequest)
}