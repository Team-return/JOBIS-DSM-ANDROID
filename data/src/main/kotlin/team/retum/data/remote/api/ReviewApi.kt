package team.retum.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import team.retum.data.remote.request.review.PostReviewRequest
import team.retum.data.remote.response.review.FetchReviewDetailsResponse
import team.retum.data.remote.response.review.FetchReviewsResponse
import team.retum.data.remote.url.JobisUrl

interface ReviewApi {
    @GET(JobisUrl.Review.reviews)
    suspend fun fetchReviews(
        @Path("company-id") companyId: Long,
    ): FetchReviewsResponse

    @GET(JobisUrl.Review.reviewDetails)
    suspend fun fetchReviewDetails(
        @Path("review-id") reviewId: String,
    ): FetchReviewDetailsResponse

    @POST(JobisUrl.Review.postReview)
    suspend fun postReview(
        @Body postReviewRequest: PostReviewRequest,
    )
}
