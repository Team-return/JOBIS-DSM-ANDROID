package team.retum.jobis_android.feature.application

import androidx.annotation.StringRes
import team.retum.domain.param.application.AttachmentsParam
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class ApplicationState(
    val recruitmentId: Long = 0,
    val attachments: List<AttachmentsParam> = emptyList(),
    val isReApply: Boolean = false,
) : State

internal sealed class ApplicationSideEffect : SideEffect {
    object SuccessApplyCompany : ApplicationSideEffect()
    object RecruitmentNotFound : ApplicationSideEffect()
    object ApplyConflict : ApplicationSideEffect()
    object InvalidFileExtension : ApplicationSideEffect()
    class Exception(@StringRes val message: Int) : ApplicationSideEffect()
}
