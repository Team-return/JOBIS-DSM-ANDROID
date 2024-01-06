package team.retum.jobis_android.feature.recruitment

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.data.remote.url.JobisUrl
import team.retum.domain.entity.recruitment.RecruitmentEntity
import team.retum.domain.param.recruitment.FetchRecruitmentsParam
import team.retum.domain.usecase.bookmark.BookmarkRecruitmentUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentCountUseCase
import team.retum.domain.usecase.recruitment.FetchRecruitmentsUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
class RecruitmentsScreenViewModel @Inject constructor(
    private val fetchRecruitmentsUseCase: FetchRecruitmentsUseCase,
    private val bookmarkRecruitmentUseCase: BookmarkRecruitmentUseCase,
    private val fetchRecruitmentCountUseCase: FetchRecruitmentCountUseCase,
) : BaseViewModel<RecruitmentsState, RecruitmentsSideEffect>() {

    override val container: Container<RecruitmentsState, RecruitmentsSideEffect> =
        container(RecruitmentsState())

    internal var recruitments: SnapshotStateList<RecruitmentEntity> = mutableStateListOf()
        private set

    init {
        fetchRecruitments()
        fetchRecruitmentCount()
    }

    private fun fetchRecruitments() = intent {
        reduce { state.copy(page = state.page + 1) }
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentsUseCase(
                fetchRecruitmentsParam = FetchRecruitmentsParam(
                    page = state.page,
                    name = state.name,
                    jobCode = state.jobCode,
                    techCode = state.techCode,
                    winterIntern = state.isWinterIntern,
                ),
            ).onSuccess { it ->
                recruitments.addAll(
                    it.recruitmentEntities.map {
                        val profileImageUrl = JobisUrl.imageUrl + it.companyProfileUrl
                        it.copy(companyProfileUrl = profileImageUrl)
                    },
                )
            }
        }
    }

    private fun fetchRecruitmentCount() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentCountUseCase(
                FetchRecruitmentsParam(
                    page = state.page,
                    name = state.name,
                    jobCode = state.jobCode,
                    techCode = state.techCode,
                    winterIntern = state.isWinterIntern,
                ),
            ).onSuccess {
                reduce { state.copy(totalPage = it.totalPageCount) }
            }
        }
    }

    internal fun Flow<Int?>.callNextPageByPosition() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchNextPage = async {
                this@callNextPageByPosition.collect {
                    it?.run {
                        if (this == recruitments.lastIndex - 2) {
                            fetchRecruitments()
                        }
                    }
                }
            }
            fetchNextPage.start()
            container.stateFlow.collect {
                if (it.page == state.totalPage && state.totalPage != 0L) {
                    fetchNextPage.cancel()
                }
            }
        }
    }

    internal fun bookmark(
        recruitmentEntity: RecruitmentEntity,
        index: Int,
    ) = intent {
        val bookmarked = recruitmentEntity.bookmarked
        val recruitmentId = recruitmentEntity.recruitId
        recruitments[index] = recruitmentEntity.copy(bookmarked = !bookmarked)
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRecruitmentUseCase(recruitmentId = recruitmentId).onSuccess {
            }.onFailure {
            }
        }
    }

    internal fun setWinterIntern(isWinterIntern: Boolean) = intent {
        reduce { state.copy(isWinterIntern = isWinterIntern) }
    }

    internal fun setName(name: String) = intent {
        reduce { state.copy(name = name) }
    }

    internal fun setCodes(
        jobCode: Long?,
        techCode: String,
    ) = intent {
        reduce {
            state.copy(
                jobCode = jobCode,
                techCode = techCode,
            )
        }
    }
}

data class RecruitmentsState(
    val page: Long = 0,
    val name: String? = null,
    val jobCode: Long? = null,
    val techCode: String? = null,
    val isWinterIntern: Boolean = false,
    val totalPage: Long = 0,
) : State

sealed interface RecruitmentsSideEffect : SideEffect
