package team.retum.data.remote.datasource.application

import team.retum.data.remote.request.application.ApplyCompanyRequest
import team.retum.data.remote.response.application.FetchAppliedCompanyHistoriesResponse
import team.retum.data.remote.response.application.FetchTotalPassedStudentCountResponse

interface ApplicationDataSource {
    suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse

    suspend fun fetchAppliedCompanyHistories(): FetchAppliedCompanyHistoriesResponse

    suspend fun applyCompany(
        recruitmentId: Long,
        applyCompanyRequest: ApplyCompanyRequest,
    )

    suspend fun reApplyCompany(
        applicationId: Long,
        applyCompanyRequest: ApplyCompanyRequest,
    )
}
