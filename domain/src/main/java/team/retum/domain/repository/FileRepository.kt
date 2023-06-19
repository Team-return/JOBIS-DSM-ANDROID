package team.retum.domain.repository

import team.retum.domain.entity.FileType
import team.retum.domain.entity.UploadFileEntity
import java.io.File

interface FileRepository {
    suspend fun uploadFile(
        type: FileType,
        file: List<File>,
    ): UploadFileEntity
}