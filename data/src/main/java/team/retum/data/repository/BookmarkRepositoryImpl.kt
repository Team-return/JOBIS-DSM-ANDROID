package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.BookmarkDataSource
import team.retum.data.remote.response.bookmark.toEntity
import team.retum.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDataSource: BookmarkDataSource,
) : BookmarkRepository {

    override suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    ) {
        bookmarkDataSource.bookmarkRecruitment(
            recruitmentId = recruitmentId,
        )
    }

    override suspend fun fetchBookmarkedRecruitments() =
        bookmarkDataSource.fetchBookmarkedRecruitments().toEntity()

}