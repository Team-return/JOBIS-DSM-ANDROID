package team.retum.jobis_android.contract.bookmark

import team.retum.domain.entity.bookmark.BookmarkedRecruitmentEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class BookmarkState(
    val bookmarkedRecruitments: List<BookmarkedRecruitmentEntity> = emptyList(),
    val bookmarkExists: Boolean = true,
): State

sealed class BookmarkSideEffect: SideEffect{

}