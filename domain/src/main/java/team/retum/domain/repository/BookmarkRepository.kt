package team.retum.domain.repository

import team.retum.domain.entity.bookmark.BookmarkedRecruitmentsEntity

interface BookmarkRepository {
    suspend fun fetchBookmarkedRecruitments(): BookmarkedRecruitmentsEntity
}