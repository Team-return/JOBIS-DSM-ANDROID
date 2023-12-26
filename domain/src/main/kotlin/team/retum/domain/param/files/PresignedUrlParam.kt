package team.retum.domain.param.files

import team.retum.domain.entity.FileType

data class PresignedUrlParam(
    val type: FileType,
    val fileName: String,
)
