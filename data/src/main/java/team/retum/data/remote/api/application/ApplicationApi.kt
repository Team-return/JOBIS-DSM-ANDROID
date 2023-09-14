package team.retum.data.remote.api.application

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import team.retum.data.remote.request.application.ApplyCompanyRequest
import team.retum.data.remote.response.applications.FetchAppliedCompanyHistoriesResponse
import team.retum.data.remote.response.applications.FetchTotalPassedStudentCountResponse
import team.retum.data.remote.url.JobisUrl

interface ApplicationApi {
    @GET(JobisUrl.Applications.students)
    suspend fun fetchAppliedCompanyHistories(): FetchAppliedCompanyHistoriesResponse

    @GET(JobisUrl.Applications.employment)
    suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse

    @POST(JobisUrl.Applications.apply)
    suspend fun applyCompany(
        @Path("recruitment-id") recruitmentId: Long,
        @Body applyCompanyRequest: ApplyCompanyRequest,
    )
}