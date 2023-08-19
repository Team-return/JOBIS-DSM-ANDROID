package team.retum.data.remote.response.company

import com.google.gson.annotations.SerializedName

data class FetchReviewableCompaniesResponse(
    @SerializedName("companies") val companies: List<ReviewableCompany>,
)

data class ReviewableCompany(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
)