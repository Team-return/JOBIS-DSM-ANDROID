package team.retum.jobis_android.contract

import team.retum.domain.entity.FileType
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.io.File

data class FileState(
    val type: FileType = FileType.EXTENSION_FILE,
    val files: MutableList<File> = mutableListOf(),
): State

sealed class FileSideEffect: SideEffect{

}