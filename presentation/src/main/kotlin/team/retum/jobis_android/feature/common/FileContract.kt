package team.retum.jobis_android.feature.common

import team.retum.domain.entity.FileType
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class FileState(
    val type: FileType = FileType.EXTENSION_FILE,
    val urls: MutableList<String> = mutableListOf(),
) : State

sealed class FileSideEffect : SideEffect {
    object Success : FileSideEffect()
    object InvalidFileExtension : FileSideEffect()
    class Exception(val message: String) : FileSideEffect()
}
