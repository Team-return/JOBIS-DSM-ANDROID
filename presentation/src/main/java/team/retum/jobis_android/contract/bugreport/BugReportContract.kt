package team.retum.jobis_android.contract.bugreport

import android.net.Uri
import team.retum.domain.enums.DevelopmentArea
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class BugState(
    val title: String = "",
    val content: String = "",
    val titleError: Boolean = false,
    val contentError: Boolean = false,
    val selectedPosition: DevelopmentArea = DevelopmentArea.ALL,
    val fileUrls: List<String> = listOf(),
    val uris: MutableList<Uri> = mutableListOf(),
    val reportBugButtonState: Boolean = false,
) : State

sealed class BugSideEffect : SideEffect {
    object SuccessReportBug : BugSideEffect()
    class Exception(val message: String) : BugSideEffect()
}