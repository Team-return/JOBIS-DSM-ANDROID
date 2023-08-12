package team.retum.jobis_android.viewmodel.review

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
import team.retum.domain.usecase.review.FetchReviewDetailsUseCase
import team.retum.domain.usecase.review.FetchReviewsUseCase
import team.retum.jobis_android.contract.ReviewSideEffect
import team.retum.jobis_android.contract.ReviewState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val fetchReviewsUseCase: FetchReviewsUseCase,
    private val fetchReviewDetailsUseCase: FetchReviewDetailsUseCase,
) : BaseViewModel<ReviewState, ReviewSideEffect>() {

    override val container = container<ReviewState, ReviewSideEffect>(ReviewState())

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
                setWriter(it.writer)
                setInterviews(it.qnaResponse)
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

    private fun setWriter(
        writer: String,
    ) = intent {
        reduce {
            state.copy(
                writer = writer,
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
}
