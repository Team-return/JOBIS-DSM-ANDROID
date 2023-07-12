package team.retum.jobis_android.contract

import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import team.retum.jobis_android.viewmodel.recruitment.RecruitmentUiModel

data class RecruitmentState(
    var page: Int = 1,
    var jobCode: Long? = null,
    var techCode: String? = null,
    var name: String? = null,
    var recruitmentId: Long = 0L,
    var details: RecruitmentDetailsEntity = RecruitmentDetailsEntity(
        areas = emptyList(),
        benefits = null,
        companyId = 0,
        companyName = "",
        companyProfileUrl = "",
        endDate = "",
        etc = null,
        hiringProgress = emptyList(),
        military = false,
        pay = 0,
        preferentialTreatment = null,
        requiredGrade = null,
        requiredLicenses = null,
        startDate = "",
        submitDocument = "",
        trainPay = 0,
        workHours = 0,
    ),
    val recruitments: List<RecruitmentUiModel> = mutableListOf(),
): State

sealed class RecruitmentSideEffect: SideEffect{
    class Exception(
        val message: String,
    ): RecruitmentSideEffect()
}

