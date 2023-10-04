package team.retum.data.remote.response.company

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.company.CompanyCountEntity

data class FetchCompanyCountResponse(
    @SerializedName("total_page_count") val totalPageCount: Long,
)

fun FetchCompanyCountResponse.toEntity() = CompanyCountEntity(
    totalPageCount = totalPageCount,
)