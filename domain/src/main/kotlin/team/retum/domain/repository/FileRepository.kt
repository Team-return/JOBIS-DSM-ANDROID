package team.retum.domain.repository

import team.retum.domain.entity.FileType
import team.retum.domain.entity.UploadFileEntity
import team.retum.domain.entity.files.PresignedUrlEntity
import team.retum.domain.param.files.PresignedUrlParam
import java.io.File

interface FileRepository {
    suspend fun uploadFile(
        type: FileType,
        files: List<File>,
    ): UploadFileEntity

    suspend fun createPresignedUrl(presignedUrlParam: PresignedUrlParam): PresignedUrlEntity
}
