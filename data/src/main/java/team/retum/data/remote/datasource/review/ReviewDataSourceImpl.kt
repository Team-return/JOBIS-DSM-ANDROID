package team.retum.data.remote.datasource.review

import team.retum.data.remote.api.review.ReviewApi
import team.retum.data.remote.request.review.PostReviewRequest
import team.retum.data.remote.response.review.FetchReviewDetailsResponse
import team.retum.data.remote.response.review.FetchReviewsResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(
    private val reviewApi: ReviewApi,
): ReviewDataSource {
    override suspend fun fetchReviews(
        companyId: Long,
    ): FetchReviewsResponse = HttpHandler<FetchReviewsResponse>().httpRequest {
        reviewApi.fetchReviews(
            companyId = companyId,
        )
    }.sendRequest()

    override suspend fun fetchReviewDetails(
        reviewId: String,
    ): FetchReviewDetailsResponse = HttpHandler<FetchReviewDetailsResponse>().httpRequest {
        reviewApi.fetchReviewDetails(
            reviewId = reviewId,
        )
    }.sendRequest()

    override suspend fun postReview(postReviewRequest: PostReviewRequest) = HttpHandler<Unit>().httpRequest {
        reviewApi.postReview(postReviewRequest)
    }.sendRequest()
}