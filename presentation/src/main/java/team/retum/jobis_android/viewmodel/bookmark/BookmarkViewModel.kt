package team.retum.jobis_android.viewmodel.bookmark

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.bookmark.BookmarkedRecruitmentEntity
import team.retum.domain.usecase.bookmark.BookmarkRecruitmentUseCase
import team.retum.domain.usecase.bookmark.FetchBookmarkedRecruitmentsUseCase
import team.retum.jobis_android.contract.BookmarkSideEffect
import team.retum.jobis_android.contract.BookmarkState
import team.retum.jobis_android.contract.RecruitmentSideEffect
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

internal class BookmarkViewModel @Inject constructor(
    private val bookmarkRecruitmentUseCase: BookmarkRecruitmentUseCase,
    private val fetchBookmarkedRecruitmentsUseCase: FetchBookmarkedRecruitmentsUseCase,
): BaseViewModel<BookmarkState, BookmarkSideEffect>() {

    override fun sendEvent(event: Event) {}

    override val container = container<BookmarkState, BookmarkSideEffect>(BookmarkState())

    private fun bookmarkRecruitment(
        recruitmentId: Long,
    ) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRecruitmentUseCase(
                recruitmentId = recruitmentId,
            )
        }
    }

    private fun fetchBookmarkedRecruitments() = intent{
        viewModelScope.launch(Dispatchers.IO){
            fetchBookmarkedRecruitmentsUseCase().onSuccess {
                setBookmarkedRecruitments(bookmarkedRecruitments = it.bookmarks)
            }.onFailure {

            }
        }
    }

    private fun setBookmarkedRecruitments(
        bookmarkedRecruitments: List<BookmarkedRecruitmentEntity>,
    ) = intent{
        reduce {
            state.copy(
                bookmarkedRecruitments = bookmarkedRecruitments,
            )
        }
    }
}