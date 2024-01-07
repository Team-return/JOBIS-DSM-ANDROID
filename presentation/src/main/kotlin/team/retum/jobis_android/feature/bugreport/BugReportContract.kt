package team.retum.jobis_android.feature.bugreport

import androidx.annotation.StringRes
import team.retum.domain.enums.DevelopmentArea
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class BugState(
    val title: String = "",
    val content: String = "",
    val titleError: Boolean = false,
    val contentError: Boolean = false,
    val selectedPosition: DevelopmentArea = DevelopmentArea.ALL,
    val reportBugButtonState: Boolean = false,
) : State

sealed class BugSideEffect : SideEffect {
    object SuccessReportBug : BugSideEffect()
    class Exception(@StringRes val message: Int) : BugSideEffect()
}
