package team.retum.data.remote.datasource.file

import okhttp3.MultipartBody
import team.retum.data.remote.request.files.CreatePresignedUrlRequest
import team.retum.data.remote.request.files.CreatePresignedUrlResponse
import team.retum.data.remote.response.file.UploadFileResponse
import team.retum.domain.entity.FileType

interface FileDataSource {
    suspend fun uploadFile(
        type: FileType,
        files: List<MultipartBody.Part>,
    ): UploadFileResponse

    suspend fun createPresignedUrl(createPresignedUrlRequest: CreatePresignedUrlRequest): CreatePresignedUrlResponse
}
