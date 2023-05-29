package team.retum.domain.repository

import team.retum.domain.entity.applications.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.applications.TotalPassedStudentCountEntity

interface ApplicationsRepository {
    suspend fun fetchPassedStudentCount(): TotalPassedStudentCountEntity

    suspend fun fetchAppliedCompanyHistories(): AppliedCompanyHistoriesEntity
}