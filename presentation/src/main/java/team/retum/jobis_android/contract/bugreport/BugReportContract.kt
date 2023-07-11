package team.retum.jobis_android.contract.bugreport

import android.net.Uri
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

internal data class BugReportState(
    val title: String = "",
    val content: String = "",
    val titleError: Boolean = false,
    val contentError: Boolean = false,
    val selectedPosition: Position = Position.All,
    val uriList: List<Uri> = listOf()
) : State

sealed class BugReportSideEffect : SideEffect {

}

enum class Position {
    All, Server, iOS, Android, Web
}