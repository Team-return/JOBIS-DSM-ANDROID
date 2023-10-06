package team.retum.domain.param.review

data class PostReviewParam(
    val companyId: Long,
    val qnaElements: List<QnaElementParam>,
)

data class QnaElementParam(
    val question: String,
    val answer: String,
    val codeId: Long,
)
