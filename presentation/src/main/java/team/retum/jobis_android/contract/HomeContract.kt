package team.retum.jobis_android.contract

import team.retum.domain.entity.ApplyCompaniesEntity
import team.retum.domain.entity.UserApplyCompaniesEntity
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class HomeState(
    val name: String = "",
    val gcn: String = "",
    val applyCompanies: List<ApplyCompaniesEntity> = emptyList(),
    val totalStudentCont: Int = 0,
    val passCount: Int = 0,
    val approvedCount: Int = 0,
) : State

sealed class HomeSideEffect : SideEffect {
    class SuccessFetchMainPageInformations(
        val mainPageInformations: UserApplyCompaniesEntity,
    ) : HomeSideEffect()

    class Exception(
        val message: String,
    ) : HomeSideEffect()
}

sealed class HomeEvent : Event {
    object FetchUserApplyCompanies : HomeEvent()
}