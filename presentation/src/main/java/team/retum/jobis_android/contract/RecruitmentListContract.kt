package team.retum.jobis_android.contract

import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.entity.recruitment.RecruitmentsEntity
import team.retum.jobis_android.util.mvi.Event
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class RecruitmentState(
    var key: Int = 1,
    var keyword: String = "",
    var company: String = "",
    var recruitmentId: Long = 0L,
    var details: RecruitmentDetailsEntity = RecruitmentDetailsEntity(
        areas = emptyList(),
        preferentialTreatment = null,
        requiredGrade = null,
        workHours = 0,
        requiredLicenses = null,
        hiringProgress = emptyList(),
        trainPay = 0,
        pay = null,
        benefits = "",
        military = false,
        submitDocument = "",
        startDate = "",
        endDate = "",
        etc = null,
    )
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

