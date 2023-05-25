package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.FetchTotalPassedStudentCountResponse

interface ApplicationsDataSource {
    suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse
}