package team.retum.data.remote.datasource.application

import team.retum.data.remote.request.application.ApplyCompanyRequest
import team.retum.data.remote.response.applications.FetchAppliedCompanyHistoriesResponse
import team.retum.data.remote.response.applications.FetchTotalPassedStudentCountResponse

interface ApplicationsDataSource {
    suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse

    suspend fun fetchAppliedCompanyHistories(): FetchAppliedCompanyHistoriesResponse

    suspend fun applyCompany(
        recruitmentId: Long,
        applyCompanyRequest: ApplyCompanyRequest,
    )
}