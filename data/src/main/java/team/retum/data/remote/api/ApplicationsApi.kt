package team.retum.data.remote.api

import retrofit2.http.GET
import team.retum.data.remote.response.applications.FetchAppliedCompanyHistoriesResponse
import team.retum.data.remote.response.applications.FetchTotalPassedStudentCountResponse
import team.retum.data.remote.url.JobisUrl

interface ApplicationsApi {
    @GET(JobisUrl.Applications.employment)
    suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse

    @GET(JobisUrl.Applications.students)
    suspend fun fetchAppliedCompanyHistories(): FetchAppliedCompanyHistoriesResponse
}