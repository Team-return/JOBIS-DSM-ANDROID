package team.retum.jobis_android.contract

import android.net.Uri
import team.retum.domain.entity.FileType
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class FileState(
    val type: FileType = FileType.EXTENSION_FILE,
    val files: List<Uri> = emptyList(),
): State

sealed class FileSideEffect: SideEffect{

}