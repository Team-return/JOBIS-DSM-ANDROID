package team.retum.jobis_android.feature.review

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.enums.Type
import team.retum.domain.param.code.FetchCodesParam
import team.retum.domain.param.review.PostReviewParam
import team.retum.domain.param.review.QnaElementParam
import team.retum.domain.usecase.code.FetchCodesUseCase
import team.retum.domain.usecase.review.PostReviewUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
internal class PostReviewScreenViewModel @Inject constructor(
    private val postReviewUseCase: PostReviewUseCase,
    private val fetchCodesUseCase: FetchCodesUseCase,
) : BaseViewModel<PostReviewState, PostReviewSideEffect>() {
    override val container = container<PostReviewState, PostReviewSideEffect>(PostReviewState())

    var qnaElements: SnapshotStateList<QnaElementParam> = mutableStateListOf()
        private set
    var techs: SnapshotStateList<CodeEntity> = mutableStateListOf()
        private set

    init {
        addQnaElement()
        fetchCodes()
    }

    internal fun postReview() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            postReviewUseCase(
                postReviewParam = PostReviewParam(
                    companyId = state.companyId,
                    qnaElements = qnaElements,
                ),
            ).onSuccess {
                postSideEffect(PostReviewSideEffect.Success)
            }.onFailure {
                postSideEffect(PostReviewSideEffect.Exception(getStringFromException(it)))
            }
        }
    }

    private fun fetchCodes() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCodesUseCase(
                fetchCodesParam = FetchCodesParam(
                    keyword = null,
                    type = Type.TECH,
                    parentCode = null,
                ),
            ).onSuccess {
                techs.addAll(it.codes)
            }
        }
    }

    internal fun addQnaElement() {
        qnaElements.add(
            QnaElementParam(
                question = "",
                answer = "",
                codeId = 0,
            ),
        )
    }

    internal fun setQuestion(
        question: String,
        index: Int,
    ) {
        qnaElements[index] = qnaElements[index].copy(question = question)
    }

    internal fun setAnswer(
        answer: String,
        index: Int,
    ) {
        qnaElements[index] = qnaElements[index].copy(answer = answer)
    }

    internal fun setJobCode(
        jobCode: Long,
        index: Int,
    ) {
        qnaElements[index] = qnaElements[index].copy(codeId = jobCode)
    }

    internal fun setCompanyId(companyId: Long) = intent {
        reduce { state.copy(companyId = companyId) }
    }
}

internal data class PostReviewState(
    val companyId: Long = 0,
) : State
internal sealed interface PostReviewSideEffect : SideEffect {
    object Success : PostReviewSideEffect
    class Exception(val message: Int) : PostReviewSideEffect
}
