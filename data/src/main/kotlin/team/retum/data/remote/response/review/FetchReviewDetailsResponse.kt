package team.retum.data.remote.response.review

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.review.ReviewDetailEntity
import team.retum.domain.entity.review.ReviewDetailsEntity

data class FetchReviewDetailsResponse(
    @SerializedName("qna_responses") val qnaResponses: List<QnaResponse>,
)

data class QnaResponse(
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String,
    @SerializedName("area") val area: String,
)

internal fun FetchReviewDetailsResponse.toEntity() = ReviewDetailsEntity(
    qnaResponse = this.qnaResponses.map { it.toEntity() },
)

private fun QnaResponse.toEntity() = ReviewDetailEntity(
    question = this.question,
    answer = this.answer,
    area = this.area,
)