package team.retum.data.remote.api

import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import team.retum.data.remote.response.RecruitmentListResponse
import team.retum.data.remote.url.JobisUrl

interface RecruitmentApi {
    @GET(JobisUrl.Recruitment.student)
    suspend fun fetchRecruitmentList(
        @Query("page") page: Int,
        @Query("code") code: Long?,
        @Query("company") company: String?,
    ): RecruitmentListResponse

    @PATCH(JobisUrl.Recruitment.bookmark)
    suspend fun bookmarkRecruitment(
        @Path("recruitment-id") recruitmentId: Long,
    )
}