package team.retum.jobis_android.feature.recruitment

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class RecruitmentState(
    var page: Int = 1,
    var recruitmentCount: Long = 0,
    var jobCode: Long? = null,
    var techCode: String? = null,
    var name: String? = null,
    val isWinterIntern: Boolean = false,
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
    val recruitments: SnapshotStateList<RecruitmentUiModel> = mutableStateListOf(),
) : State

sealed class RecruitmentSideEffect : SideEffect {
    class Exception(val message: String) : RecruitmentSideEffect()
}