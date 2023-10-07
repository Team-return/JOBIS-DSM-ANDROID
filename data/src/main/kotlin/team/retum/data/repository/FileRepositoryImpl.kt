package team.retum.data.repository

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import team.retum.data.remote.datasource.file.FileDataSource
import team.retum.data.remote.response.file.toEntity
import team.retum.domain.entity.FileType
import team.retum.domain.entity.UploadFileEntity
import team.retum.domain.repository.FileRepository
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
) : FileRepository {
    override suspend fun uploadFile(
        type: FileType,
        files: List<File>,
    ): UploadFileEntity = fileDataSource.uploadFile(
        type = type,
        files = files.map { it.getImageMultipart(key = "file") },
    ).toEntity()
}

private fun File.getImageMultipart(key: String): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        name = key,
        filename = name,
        body = asRequestBody("multipart/form-data".toMediaType()),
    )
