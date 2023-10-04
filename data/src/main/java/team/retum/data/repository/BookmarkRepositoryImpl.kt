package team.retum.data.repository

import team.retum.data.remote.datasource.bookmark.BookmarkDataSource
import team.retum.data.remote.response.bookmark.toEntity
import team.retum.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDataSource: BookmarkDataSource,
) : BookmarkRepository {

    override suspend fun fetchBookmarkedRecruitments() =
        bookmarkDataSource.fetchBookmarkedRecruitments().toEntity()

    override suspend fun bookmarkRecruitment(recruitmentId: Long) {
        bookmarkDataSource.bookmarkRecruitment(recruitmentId)
    }

}