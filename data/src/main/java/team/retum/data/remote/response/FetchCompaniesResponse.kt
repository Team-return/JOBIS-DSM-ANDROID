package team.retum.data.remote.response

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.CompaniesEntity
import team.retum.domain.entity.CompanyEntity

data class FetchCompaniesResponse(
    @SerializedName("companies") val companies: List<Company>,
)

data class Company(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo_url") val logoUrl: String,
    @SerializedName("take") val take: Float,
)

fun FetchCompaniesResponse.toEntity() = CompaniesEntity(
    companies = this.companies.map { it.toEntity() },
)

private fun Company.toEntity() = CompanyEntity(
    id = this.id,
    name = this.name,
    logoUrl = this.logoUrl,
    take = this.take,
)
