package team.retum.data.remote.api

import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import team.retum.data.remote.response.bookmark.FetchBookmarkedRecruitmentsResponse
import team.retum.data.remote.url.JobisUrl

interface BookmarkApi {
    @GET(JobisUrl.bookmarks)
    suspend fun fetchBookmarkedRecruitments(): FetchBookmarkedRecruitmentsResponse

    @PATCH(JobisUrl.Bookmark.bookmark)
    suspend fun bookmarkRecruitment(
        @Path("recruitment-id") recruitmentId: Long,
    )
}
