package team.retum.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import team.retum.data.remote.response.review.FetchReviewsResponse
import team.retum.data.remote.url.JobisUrl

interface ReviewApi {
    @GET(JobisUrl.Review.reviews)
    suspend fun fetchReviews(
        @Path("company-id") companyId: Long,
    ): FetchReviewsResponse
}