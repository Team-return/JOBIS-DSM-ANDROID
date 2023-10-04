package team.retum.data.remote.response.company

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.company.CompaniesEntity
import team.retum.domain.entity.company.CompanyEntity

data class FetchCompaniesResponse(
    @SerializedName("companies") val companies: List<Company>,
)

data class Company(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("logo_url") val logoUrl: String,
    @SerializedName("take") val take: Float,
    @SerializedName("has_recruitment") val hasRecruitment: Boolean,
)

fun FetchCompaniesResponse.toEntity() = CompaniesEntity(
    companies = this.companies.map { it.toEntity() },
)

private fun Company.toEntity() = CompanyEntity(
    id = this.id,
    name = this.name,
    logoUrl = this.logoUrl,
    take = this.take,
    hasRecruitment = this.hasRecruitment,
)
