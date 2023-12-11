package team.retum.jobis_android.feature.review

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import team.retum.domain.entity.review.ReviewDetailEntity
import team.retum.domain.entity.review.ReviewEntity
import team.retum.domain.param.review.QnaElementParam
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class ReviewState(
    var companyId: Long = 0L,
    var reviews: List<ReviewEntity> = emptyList(),
    val reviewId: String = "",
    val writer: String = "",
    val reviewDetails: List<ReviewDetailEntity> = emptyList(),
    val qnaElements: SnapshotStateList<QnaElementParam> = mutableStateListOf(),
) : State

sealed class ReviewSideEffect : SideEffect {
    object SuccessPostReview : ReviewSideEffect()
    class Exception(val message: String) : ReviewSideEffect()
}
