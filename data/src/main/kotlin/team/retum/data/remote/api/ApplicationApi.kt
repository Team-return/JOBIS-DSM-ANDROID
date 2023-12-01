package team.retum.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import team.retum.data.remote.request.application.ApplyCompanyRequest
import team.retum.data.remote.response.application.FetchAppliedCompanyHistoriesResponse
import team.retum.data.remote.response.application.FetchTotalPassedStudentCountResponse
import team.retum.data.remote.url.JobisUrl

interface ApplicationApi {
    @GET(JobisUrl.Application.students)
    suspend fun fetchAppliedCompanyHistories(): FetchAppliedCompanyHistoriesResponse

    @GET(JobisUrl.Application.employment)
    suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse

    @POST(JobisUrl.Application.apply)
    suspend fun applyCompany(
        @Path("recruitment-id") recruitmentId: Long,
        @Body applyCompanyRequest: ApplyCompanyRequest,
    )

    @PATCH(JobisUrl.Application.reApply)
    suspend fun reApplyCompany(
        @Path("application-id") applicationId: Long,
        @Body applyCompanyRequest: ApplyCompanyRequest,
    )
}
