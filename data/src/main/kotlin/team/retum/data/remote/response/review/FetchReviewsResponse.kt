package team.retum.data.remote.response.review

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.review.ReviewEntity
import team.retum.domain.entity.review.ReviewsEntity

data class FetchReviewsResponse(
    @SerializedName("reviews") val reviews: List<Review>,
)

data class Review(
    @SerializedName("review_id") val reviewId: String,
    @SerializedName("year") val year: Long,
    @SerializedName("writer") val writer: String,
)

fun FetchReviewsResponse.toEntity() = ReviewsEntity(
    reviews = this.reviews.map { it.toEntity() },
)

private fun Review.toEntity() = ReviewEntity(
    reviewId = this.reviewId,
    year = this.year,
    writer = this.writer,
)
