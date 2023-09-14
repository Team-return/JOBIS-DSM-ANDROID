package team.retum.data.remote.api

import retrofit2.http.GET
import team.retum.data.remote.response.bookmark.FetchBookmarkedRecruitmentsResponse
import team.retum.data.remote.url.JobisUrl

interface BookmarkApi {
    @GET(JobisUrl.bookmarks)
    suspend fun fetchBookmarkedRecruitments(): FetchBookmarkedRecruitmentsResponse
}