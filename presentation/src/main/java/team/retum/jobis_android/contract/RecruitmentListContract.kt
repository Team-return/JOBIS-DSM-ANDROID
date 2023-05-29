package team.retum.jobis_android.contract

import team.retum.domain.entity.RecruitmentsEntity
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class RecruitmentState(
    var key: Int = 1,
    var keyword: String = "",
    var company: String = "",
): State

sealed class RecruitmentSideEffect: SideEffect{
    class SuccessFetchRecruitmentsSideEffect(
        val recruitmentsEntity: RecruitmentsEntity,
    ): RecruitmentSideEffect()

    class Exception(
        val message: String,
    ): RecruitmentSideEffect()
}

sealed class RecruitmentEvent: Event{
    class FetchRecruitments(
        val page: Int,
        val code: Long?,
        val company: String?,
    ): RecruitmentEvent()

    class BookmarkRecruitment(
        val recruitmentId: Long,
    ): RecruitmentEvent()
}
