package team.retum.jobis_android.feature.recruitment

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.entity.recruitment.RecruitmentEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class RecruitmentDetailsState(
    var recruitmentId: Long = 0L,
    var details: RecruitmentDetailsEntity = RecruitmentDetailsEntity(
        areas = emptyList(),
        benefits = null,
        companyId = 0,
        companyName = "",
        companyProfileUrl = "",
        endDate = "",
        endTime = "",
        etc = null,
        hiringProgress = emptyList(),
        military = false,
        pay = "",
        requiredGrade = null,
        requiredLicenses = null,
        startDate = "",
        startTime = "",
        submitDocument = "",
        trainPay = 0,
    ),
    val recruitments: SnapshotStateList<RecruitmentEntity> = mutableStateListOf(),
) : State

sealed interface RecruitmentDetailsSideEffect : SideEffect {
    object RecruitmentNotFound : RecruitmentDetailsSideEffect
    class Exception(@StringRes val message: Int) : RecruitmentDetailsSideEffect
}
