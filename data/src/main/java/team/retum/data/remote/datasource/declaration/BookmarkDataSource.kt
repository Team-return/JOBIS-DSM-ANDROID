package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.bookmark.FetchBookmarkedRecruitmentsResponse

interface BookmarkDataSource {
    suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    )

    suspend fun fetchBookmarkedRecruitments(): FetchBookmarkedRecruitmentsResponse
}