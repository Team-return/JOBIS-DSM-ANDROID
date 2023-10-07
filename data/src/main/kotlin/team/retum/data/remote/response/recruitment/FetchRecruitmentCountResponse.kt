package team.retum.data.remote.response.recruitment

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.recruitment.RecruitmentCountEntity

data class FetchRecruitmentCountResponse(
    @SerializedName("total_page_count") val totalPageCount: Long,
)

fun FetchRecruitmentCountResponse.toEntity() = RecruitmentCountEntity(
    totalPageCount = totalPageCount,
)
