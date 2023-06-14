package team.retum.jobis_android.viewmodel.bookmark

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.bookmark.BookmarkedRecruitmentEntity
import team.retum.domain.usecase.bookmark.BookmarkRecruitmentUseCase
import team.retum.domain.usecase.bookmark.FetchBookmarkedRecruitmentsUseCase
import team.retum.jobis_android.contract.BookmarkSideEffect
import team.retum.jobis_android.contract.BookmarkState
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class BookmarkViewModel @Inject constructor(
    private val bookmarkRecruitmentUseCase: BookmarkRecruitmentUseCase,
    private val fetchBookmarkedRecruitmentsUseCase: FetchBookmarkedRecruitmentsUseCase,
) : BaseViewModel<BookmarkState, BookmarkSideEffect>() {

    override fun sendEvent(event: Event) {}

    override val container = container<BookmarkState, BookmarkSideEffect>(BookmarkState())

    internal fun bookmarkRecruitment(
        recruitmentId: Long,
    ) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRecruitmentUseCase(
                recruitmentId = recruitmentId,
            ).onSuccess {
                fetchBookmarkedRecruitments()
            }
        }
    }

    internal fun fetchBookmarkedRecruitments() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchBookmarkedRecruitmentsUseCase().onSuccess {
                setBookmarkExists(bookmarkExists = it.bookmarks.isNotEmpty())
                setBookmarkedRecruitments(bookmarkedRecruitments = it.bookmarks)
            }
        }
    }

    private fun setBookmarkedRecruitments(
        bookmarkedRecruitments: List<BookmarkedRecruitmentEntity>,
    ) = intent {
        reduce {
            state.copy(
                bookmarkedRecruitments = bookmarkedRecruitments,
            )
        }
    }

    private fun setBookmarkExists(
        bookmarkExists: Boolean,
    ) = intent{
        reduce {
            state.copy(
                bookmarkExists = bookmarkExists,
            )
        }
    }
}