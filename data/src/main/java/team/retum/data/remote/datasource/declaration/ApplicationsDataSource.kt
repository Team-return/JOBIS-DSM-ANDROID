package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.applications.FetchAppliedCompanyHistoriesResponse
import team.retum.data.remote.response.applications.FetchTotalPassedStudentCountResponse

interface ApplicationsDataSource {
    suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse

    suspend fun fetchAppliedCompanyHistories(): FetchAppliedCompanyHistoriesResponse
}