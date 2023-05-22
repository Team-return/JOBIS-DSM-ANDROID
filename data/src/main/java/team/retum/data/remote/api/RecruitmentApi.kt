package team.retum.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import team.retum.data.remote.response.RecruitmentListResponse
import team.retum.data.remote.url.JobisUrl

interface RecruitmentApi {
    @GET(JobisUrl.Recruitment.student)
    suspend fun fetchRecruitmentList(
        @Query("page") page: Int,
        @Query("keyword") keyword: String?,
        @Query("company") company: String?,
    ): RecruitmentListResponse
}