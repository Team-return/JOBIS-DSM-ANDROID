package team.retum.domain.entity.applications

import team.retum.domain.enums.ApplicationStatus

data class AppliedCompanyHistoriesEntity(
    val applications: List<AppliedCompanyEntity>,
)

data class AppliedCompanyEntity(
    val applicationId: Long,
    val company: String,
    val applicationStatus: ApplicationStatus,
)

