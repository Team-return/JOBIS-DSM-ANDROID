package team.retum.jobis_android.contract.file

import team.retum.domain.entity.FileType
import team.retum.jobis_android.contract.application.ApplicationSideEffect
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.io.File

data class FileState(
    val type: FileType = FileType.EXTENSION_FILE,
    val files: MutableList<File> = mutableListOf(),
    val urls: MutableList<String> = mutableListOf(),
): State

sealed class FileSideEffect: SideEffect{
    class SuccessUploadFile(val fileUrls: List<String>): FileSideEffect()
    object FileLargeException: FileSideEffect()
    class Exception(val message: String): FileSideEffect()
}