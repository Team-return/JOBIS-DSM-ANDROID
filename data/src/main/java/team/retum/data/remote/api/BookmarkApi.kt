package team.retum.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import team.retum.data.remote.response.bookmark.FetchBookmarkedRecruitmentsResponse
import team.retum.data.remote.url.JobisUrl

interface BookmarkApi {

    @PATCH(JobisUrl.Recruitment.bookmark)
    suspend fun bookmarkRecruitment(
        @Path("recruitment-id") recruitmentId: Long,
    ): Response<Void>

    @GET(JobisUrl.Recruitment.bookmark)
    suspend fun fetchBookmarkedRecruitments(): FetchBookmarkedRecruitmentsResponse

}