package team.retum.data.remote.request.review

import com.google.gson.annotations.SerializedName

data class PostReviewRequest(
    @SerializedName("company_id") val companyId: Long,
    @SerializedName("qna_elements") val qnaElements: List<QnaElement>,
    @SerializedName("application_id") val applicationId: Long,
)

data class QnaElement(
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String,
    @SerializedName("code_id") val codeId: Long,
)