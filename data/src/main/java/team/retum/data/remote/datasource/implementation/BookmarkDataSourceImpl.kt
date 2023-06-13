package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.BookmarkApi
import team.retum.data.remote.datasource.declaration.BookmarkDataSource
import team.retum.data.remote.response.bookmark.FetchBookmarkedRecruitmentsResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class BookmarkDataSourceImpl @Inject constructor(
    private val bookmarkApi: BookmarkApi,
) : BookmarkDataSource {

    override suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    ) = HttpHandler<Unit>().httpRequest {
        bookmarkApi.bookmarkRecruitment(
            recruitmentId = recruitmentId,
        )
    }.sendRequest()

    override suspend fun fetchBookmarkedRecruitments(): FetchBookmarkedRecruitmentsResponse =
        HttpHandler<FetchBookmarkedRecruitmentsResponse>().httpRequest {
            bookmarkApi.fetchBookmarkedRecruitments()
        }.sendRequest()
}