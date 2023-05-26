package team.retum.data.remote.response

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.AppliedHistoryEntity
import team.retum.domain.entity.Status

data class FetchAppliedCompanyHistoriesResponse(
    @SerializedName("applications") val applications: List<AppliedHistory>,
) {
}

data class AppliedHistory(
    @SerializedName("application_id") val applicationId: Int,
    @SerializedName("company") val company: String,
    @SerializedName("attachment_url_list") val attachmentUrlList: List<String>,
    @SerializedName("application_status") val applicationStatus: Status,
)

fun FetchAppliedCompanyHistoriesResponse.toEntity() = AppliedCompanyHistoriesEntity(
    applications = this.applications.map { it.toEntity() },
)

private fun AppliedHistory.toEntity() = AppliedHistoryEntity(
    applicationId = this.applicationId,
    company = this.company,
    attachmentUrlList = this.attachmentUrlList,
    applicationStatus = this.applicationStatus,
)