package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.FileDataSource
import team.retum.data.remote.response.file.toEntity
import team.retum.domain.entity.FileType
import team.retum.domain.entity.UploadFileEntity
import team.retum.domain.repository.FileRepository
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
): FileRepository {
    override suspend fun uploadFile(
        type: FileType,
        files: List<File>,
    ): UploadFileEntity = fileDataSource.uploadFile(
        type = type,
        files = files,
    ).toEntity()
}