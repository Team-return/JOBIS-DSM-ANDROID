package team.retum.jobis_android.contract.home

import team.retum.domain.entity.applications.AppliedCompanyEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class HomeState(
    val studentCounts: StudentCountsEntity = StudentCountsEntity(
        totalStudentCount = 0L,
        passCount = 0L,
        approvedCount = 0L,
    ),
    val appliedCompanyHistories: List<AppliedCompanyEntity> = emptyList(),
) : State

sealed class HomeSideEffect : SideEffect