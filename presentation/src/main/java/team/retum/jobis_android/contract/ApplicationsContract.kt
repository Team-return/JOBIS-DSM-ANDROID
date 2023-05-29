package team.retum.jobis_android.contract

import team.retum.domain.entity.applications.AppliedHistoryEntity
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

sealed class ApplicationsSideEffect: SideEffect{
    class SuccessFetchTotalPassedStudentCount(
        val totalStudentCount: Int,
        val passCount: Int,
        val approvedCount: Int,
    ): ApplicationsSideEffect()

    class SuccessFetchAppliedCompanyHistories(
        val applications: List<AppliedHistoryEntity>,
    ): ApplicationsSideEffect()

    class Exception(
        val message: String,
    ): ApplicationsSideEffect()
}

data class ApplicationsState(
    val totalStudentCount: Int = 0,
    val passCount: Int = 0,
    val approvedCount: Int = 0,
): State

sealed class ApplicationsEvent: Event{
    object FetchTotalPassedStudentCount: ApplicationsEvent()
    object FetchAppliedCompanyHistories: ApplicationsEvent()
}