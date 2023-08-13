package team.retum.jobis_android.contract.application

import team.retum.domain.param.application.AttachmentsParam
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class ApplicationState(
    val recruitmentId: Long = 0,
    val attachments: List<AttachmentsParam> = emptyList(),
    val buttonState: Boolean = false,
) : State

internal sealed class ApplicationSideEffect : SideEffect {
    object SuccessApplyCompany: ApplicationSideEffect()
}