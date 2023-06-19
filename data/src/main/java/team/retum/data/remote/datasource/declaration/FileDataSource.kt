package team.retum.data.remote.datasource.declaration

import okhttp3.MultipartBody
import team.retum.data.remote.api.FileType
import team.retum.data.remote.response.file.UploadFileResponse

interface FileDataSource {
    suspend fun uploadFile(
        type: FileType,
        file: List<MultipartBody.Part>,
    ): UploadFileResponse
}