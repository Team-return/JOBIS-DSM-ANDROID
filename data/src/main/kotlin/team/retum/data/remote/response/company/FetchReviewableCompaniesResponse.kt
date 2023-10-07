package team.retum.data.remote.response.company

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.company.ReviewableCompaniesEntity
import team.retum.domain.entity.company.ReviewableCompanyEntity

data class FetchReviewableCompaniesResponse(
    @SerializedName("companies") val companies: List<ReviewableCompany>,
)

data class ReviewableCompany(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
)

fun FetchReviewableCompaniesResponse.toEntity() = ReviewableCompaniesEntity(
    companies = this.companies.map { it.toEntity() },
)

fun ReviewableCompany.toEntity() = ReviewableCompanyEntity(
    id = this.id,
    name = this.name,
)
