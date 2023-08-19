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
    val fileUrls: List<String> = listOf(),
    val uris: MutableList<Uri> = mutableListOf(),
) : State

sealed class BugReportSideEffect : SideEffect {
    class Exception(val message: String): BugReportSideEffect()
}

enum class Position {
    All, Server, iOS, Android, Web
}