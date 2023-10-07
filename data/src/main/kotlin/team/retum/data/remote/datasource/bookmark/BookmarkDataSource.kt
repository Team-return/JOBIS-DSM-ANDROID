package team.retum.data.remote.datasource.bookmark

import team.retum.data.remote.response.bookmark.FetchBookmarkedRecruitmentsResponse

interface BookmarkDataSource {
    suspend fun fetchBookmarkedRecruitments(): FetchBookmarkedRecruitmentsResponse

    suspend fun bookmarkRecruitment(recruitmentId: Long)
}
