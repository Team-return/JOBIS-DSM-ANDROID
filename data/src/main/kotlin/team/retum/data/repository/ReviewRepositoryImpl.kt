package team.retum.data.repository

import team.retum.data.remote.datasource.review.ReviewDataSource
import team.retum.data.remote.request.review.toRequest
import team.retum.data.remote.response.review.toEntity
import team.retum.domain.entity.review.ReviewDetailsEntity
import team.retum.domain.entity.review.ReviewsEntity
import team.retum.domain.param.review.PostReviewParam
import team.retum.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataSource: ReviewDataSource,
) : ReviewRepository {
    override suspend fun fetchReviews(companyId: Long): ReviewsEntity =
        reviewDataSource.fetchReviews(
            companyId = companyId,
        ).toEntity()

    override suspend fun fetchReviewDetails(
        reviewId: String,
    ): ReviewDetailsEntity = reviewDataSource.fetchReviewDetails(
        reviewId = reviewId,
    ).toEntity()

    override suspend fun postReview(postReviewParam: PostReviewParam) {
        reviewDataSource.postReview(postReviewParam.toRequest())
    }
}
