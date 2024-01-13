package team.retum.jobis_android.feature.review

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.entity.review.ReviewDetailEntity
import team.retum.domain.usecase.review.FetchReviewDetailsUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import javax.inject.Inject

@HiltViewModel
internal class ReviewDetailsScreenViewModel @Inject constructor(
    private val fetchReviewDetailsUseCase: FetchReviewDetailsUseCase,
) : BaseViewModel<ReviewState, ReviewSideEffect>() {

    override val container = container<ReviewState, ReviewSideEffect>(ReviewState())

    internal fun fetchReviewDetails(reviewId: String) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchReviewDetailsUseCase(reviewId = reviewId).onSuccess {
                reduce { state.copy(reviewDetails = it.qnaResponse) }
            }.onFailure {
                postSideEffect(ReviewSideEffect.Exception(getStringFromException(it)))
            }
        }
    }
}

internal data class ReviewState(
    val reviewDetails: List<ReviewDetailEntity> = emptyList(),
) : State

sealed class ReviewSideEffect : SideEffect {
    class Exception(@StringRes val message: Int) : ReviewSideEffect()
}
