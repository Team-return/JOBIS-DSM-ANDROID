package team.retum.domain.repository

import team.retum.domain.entity.applications.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.domain.param.application.ApplyCompanyParam

interface ApplicationsRepository {
    suspend fun fetchPassedStudentCount(): StudentCountsEntity

    suspend fun fetchAppliedCompanyHistories(): AppliedCompanyHistoriesEntity

    suspend fun applyCompany(
        recruitmentId: Long,
        applyCompanyParam: ApplyCompanyParam,
    )
}
