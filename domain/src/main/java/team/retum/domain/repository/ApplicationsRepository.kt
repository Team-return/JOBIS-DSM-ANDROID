package team.retum.domain.repository

import team.retum.domain.entity.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.TotalPassedStudentCountEntity

interface ApplicationsRepository {
    suspend fun fetchPassedStudentCount(): TotalPassedStudentCountEntity

    suspend fun fetchAppliedCompanyHistories(): AppliedCompanyHistoriesEntity
}