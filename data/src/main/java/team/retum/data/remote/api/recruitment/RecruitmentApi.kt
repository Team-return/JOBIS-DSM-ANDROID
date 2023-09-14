package team.retum.data.remote.api.recruitment

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import team.retum.data.remote.response.recruitment.FetchRecruitmentDetailsResponse
import team.retum.data.remote.response.recruitment.RecruitmentsResponse
import team.retum.data.remote.url.JobisUrl

interface RecruitmentApi {
    @GET(JobisUrl.Recruitment.student)
    suspend fun fetchRecruitmentList(
        @Query("page") page: Int?,
        @Query("name") name: String?,
        @Query("job_code") jobCode: Long?,
        @Query("tech_code") techCode: String?,
    ): RecruitmentsResponse

    @GET(JobisUrl.Recruitment.details)
    suspend fun fetchRecruitmentDetails(
        @Path("recruitment-id") recruitmentId: Long,
    ): FetchRecruitmentDetailsResponse
}