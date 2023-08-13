package team.retum.jobis_android.contract.review

import team.retum.domain.entity.review.ReviewDetailEntity
import team.retum.domain.entity.review.ReviewEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class ReviewState(
    var companyId: Long = 0L,
    var reviews: List<ReviewEntity> = emptyList(),
    val reviewId: String = "",
    val writer: String = "",
    val reviewDetails: List<ReviewDetailEntity> = emptyList(),
): State

sealed class ReviewSideEffect: SideEffect{
    class Exception(val message: String): ReviewSideEffect()
}