package team.retum.domain.entity.review

data class ReviewsEntity(
    val reviews: List<ReviewEntity>,
)

data class ReviewEntity(
    val reviewId: String,
    val year: Int,
    val writer: String,
)