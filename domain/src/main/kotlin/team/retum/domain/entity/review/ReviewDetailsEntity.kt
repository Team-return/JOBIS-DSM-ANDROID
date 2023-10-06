package team.retum.domain.entity.review

data class ReviewDetailsEntity(
    val qnaResponse: List<ReviewDetailEntity>,
)

data class ReviewDetailEntity(
    val question: String,
    val answer: String,
    val area: String,
)