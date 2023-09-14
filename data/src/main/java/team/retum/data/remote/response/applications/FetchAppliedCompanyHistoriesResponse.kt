package team.retum.data.remote.response.applications

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.applications.AppliedCompanyEntity
import team.retum.domain.entity.applications.AppliedCompanyHistoriesEntity
import team.retum.domain.enums.ApplicationStatus

data class FetchAppliedCompanyHistoriesResponse(
    @SerializedName("applications") val applications: List<AppliedHistory>,
)

data class AppliedHistory(
    @SerializedName("application_id") val applicationId: Long,
    @SerializedName("company") val company: String,
    @SerializedName("application_status") val applicationStatus: ApplicationStatus,
)

fun FetchAppliedCompanyHistoriesResponse.toEntity() = AppliedCompanyHistoriesEntity(
    applications = this.applications.map { it.toEntity() },
)

private fun AppliedHistory.toEntity() = AppliedCompanyEntity(
    applicationId = this.applicationId,
    company = this.company,
    applicationStatus = this.applicationStatus,
)