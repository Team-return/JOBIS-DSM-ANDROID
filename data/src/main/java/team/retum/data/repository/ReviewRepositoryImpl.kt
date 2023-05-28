package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.ReviewDataSource
import team.retum.data.remote.response.review.toEntity
import team.retum.domain.entity.review.ReviewsEntity
import team.retum.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataSource: ReviewDataSource,
) : ReviewRepository {
    override suspend fun fetchReviews(companyId: Long): ReviewsEntity =
        reviewDataSource.fetchReviews(
            companyId = companyId,
        ).toEntity()
}