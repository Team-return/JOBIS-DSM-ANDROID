package team.retum.data.remote.datasource.declaration

import okhttp3.MultipartBody
import team.retum.data.remote.response.file.UploadFileResponse
import team.retum.domain.entity.FileType

interface FileDataSource {
    suspend fun uploadFile(
        type: FileType,
        files: List<MultipartBody.Part>,
    ): UploadFileResponse
}