package team.retum.domain.param.files

import team.retum.domain.entity.FileType

data class PresignedUrlParam(
    val files: List<File>,
) {
    data class File(
        val type: FileType,
        val fileName: String,
    )
}
