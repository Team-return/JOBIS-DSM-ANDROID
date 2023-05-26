package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.FetchAppliedCompanyHistoriesResponse
import team.retum.data.remote.response.FetchTotalPassedStudentCountResponse

interface ApplicationsDataSource {
    suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse

    suspend fun fetchAppliedCompanyHistories(): FetchAppliedCompanyHistoriesResponse
}