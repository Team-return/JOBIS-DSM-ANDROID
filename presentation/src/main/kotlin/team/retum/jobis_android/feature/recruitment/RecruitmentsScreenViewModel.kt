package team.retum.jobis_android.feature.recruitment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
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

const val SEARCH_DEBOUNCE_MILLIS = 500L

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

    var name: String? by mutableStateOf(null)
        private set

    @OptIn(FlowPreview::class)
    internal suspend fun observeName() {
        snapshotFlow { name }.debounce(SEARCH_DEBOUNCE_MILLIS).collect {
            if (it != null) {
                intent {
                    postSideEffect(RecruitmentsSideEffect.StartFetchNextPage)
                    reduce { state.copy(page = 0) }
                }
                fetchRecruitments()
                fetchRecruitmentCount()
            }
        }
    }

    internal fun fetchRecruitments(
        jobCode: Long? = null,
        techCode: String? = null,
    ) = intent {
        reduce { state.copy(page = state.page + 1) }
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentsUseCase(
                fetchRecruitmentsParam = FetchRecruitmentsParam(
                    page = state.page,
                    name = name,
                    jobCode = jobCode,
                    techCode = techCode,
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

    internal fun fetchRecruitmentCount(
        jobCode: Long? = null,
        techCode: String? = null,
    ) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecruitmentCountUseCase(
                FetchRecruitmentsParam(
                    page = state.page,
                    name = name,
                    jobCode = jobCode,
                    techCode = techCode,
                    winterIntern = state.isWinterIntern,
                ),
            ).onSuccess {
                reduce { state.copy(totalPage = it.totalPageCount) }
            }
        }
    }

    internal fun Flow<Int?>.callNextPageByPosition() = intent {
        var isStop = false
        viewModelScope.launch(Dispatchers.IO) {
            val fetchNextPage = async {
                this@callNextPageByPosition.collect {
                    it?.run {
                        if (this == recruitments.lastIndex - 2 && !isStop) {
                            fetchRecruitments()
                        }
                    }
                }
            }
            fetchNextPage.start()
            with(container) {
                launch {
                    stateFlow.collect {
                        if (it.page == state.totalPage && state.totalPage != 0L) {
                            isStop = true
                        }
                    }
                }
                launch {
                    sideEffectFlow.collect { sideEffect: SideEffect ->
                        when (sideEffect) {
                            is RecruitmentsSideEffect.StartFetchNextPage -> {
                                isStop = false
                            }
                        }
                    }
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
            bookmarkRecruitmentUseCase(recruitmentId = recruitmentId)
        }
    }

    internal fun setWinterIntern(isWinterIntern: Boolean) = intent {
        reduce { state.copy(isWinterIntern = isWinterIntern) }
    }

    internal fun setName(name: String) {
        recruitments.clear()
        this.name = name
    }

    internal fun setCodes(
        jobCode: Long?,
        techCode: String?,
    ) = intent {
        reduce {
            state.copy(
                jobCode = jobCode,
                techCode = techCode,
                page = 0,
            )
        }
        recruitments.clear()
        postSideEffect(RecruitmentsSideEffect.StartFetchNextPage)
        fetchRecruitmentCount(
            jobCode = jobCode,
            techCode = techCode,
        )
        fetchRecruitments(
            jobCode = jobCode,
            techCode = techCode,
        )
    }
}

data class RecruitmentsState(
    val page: Long = 0,
    val jobCode: Long? = null,
    val techCode: String? = null,
    val isWinterIntern: Boolean = false,
    val totalPage: Long = 0,
) : State

sealed interface RecruitmentsSideEffect : SideEffect {
    object StartFetchNextPage : RecruitmentsSideEffect
}
