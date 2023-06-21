package team.retum.jobis_android.contract

import team.retum.domain.param.application.AttachmentsParam
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class ApplicationState(
    val recruitmentId: Long = 0,
    val attachments: List<AttachmentsParam> = emptyList(),
) : State

internal sealed class ApplicationSideEffect : SideEffect {
    object SuccessApplyCompany: ApplicationSideEffect()
}