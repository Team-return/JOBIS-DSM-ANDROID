package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.ReviewApi
import team.retum.data.remote.datasource.declaration.ReviewDataSource
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
}