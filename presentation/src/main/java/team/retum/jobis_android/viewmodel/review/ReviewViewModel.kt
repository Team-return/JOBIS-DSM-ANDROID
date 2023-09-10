package team.retum.jobis_android.viewmodel.review

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.review.ReviewDetailEntity
import team.retum.domain.entity.review.ReviewEntity
import team.retum.domain.param.review.PostReviewParam
import team.retum.domain.param.review.QnaElementParam
import team.retum.domain.usecase.review.FetchReviewDetailsUseCase
import team.retum.domain.usecase.review.FetchReviewsUseCase
import team.retum.domain.usecase.review.PostReviewUseCase
import team.retum.jobis_android.contract.review.ReviewSideEffect
import team.retum.jobis_android.contract.review.ReviewState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val fetchReviewsUseCase: FetchReviewsUseCase,
    private val fetchReviewDetailsUseCase: FetchReviewDetailsUseCase,
    private val postReviewUseCase: PostReviewUseCase,
) : BaseViewModel<ReviewState, ReviewSideEffect>() {

    override val container = container<ReviewState, ReviewSideEffect>(ReviewState())

    private val qnaElements = mutableStateListOf<QnaElementParam>()

    internal fun fetchReviews() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchReviewsUseCase(
                companyId = state.companyId,
            ).onSuccess {
                setReviews(
                    reviews = it.reviews,
                )
            }.onFailure { throwable ->
                postSideEffect(
                    sideEffect = ReviewSideEffect.Exception(
                        message = getStringFromException(
                            throwable = throwable,
                        )
                    )
                )
            }
        }
    }

    internal fun fetchReviewDetails() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchReviewDetailsUseCase(
                reviewId = state.reviewId,
            ).onSuccess {
                setInterviews(it.qnaResponse)
            }
        }
    }

    internal fun postReview() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            postReviewUseCase(
                postReviewParam = PostReviewParam(
                    companyId = state.companyId,
                    qnaElements = state.qnaElements,
                )
            ).onSuccess {
                postSideEffect(ReviewSideEffect.SuccessPostReview)
            }.onFailure {

            }
        }
    }

    private fun setReviews(
        reviews: List<ReviewEntity>,
    ) = intent {
        reduce {
            state.copy(
                reviews = reviews,
            )
        }
    }

    internal fun setCompanyId(
        companyId: Long,
    ) = intent {
        reduce {
            state.copy(
                companyId = companyId,
            )
        }
    }

    internal fun setReviewId(
        reviewId: String,
    ) = intent {
        reduce {
            state.copy(
                reviewId = reviewId,
            )
        }
    }

    private fun setInterviews(
        interviews: List<ReviewDetailEntity>,
    ) = intent {
        reduce {
            state.copy(
                reviewDetails = interviews,
            )
        }
    }

    internal fun addQnaElement() = intent {
        qnaElements.add(
            QnaElementParam(
                question = "",
                answer = "",
                codeId = 0,
            ),
        )
        reduce {
            state.copy(qnaElements = qnaElements)
        }
    }

    internal fun setQuestion(
        question: String,
        index: Int,
    ) = intent {
        qnaElements[index] = qnaElements[index].copy(question = question)
    }

    internal fun setAnswer(
        answer: String,
        index: Int,
    ) = intent {
        qnaElements[index] = qnaElements[index].copy(answer = answer)
    }

    internal fun setJobCode(
        jobCode: Long,
        index: Int,
    ) = intent {
        qnaElements[index] = qnaElements[index].copy(codeId = jobCode)
    }
}
