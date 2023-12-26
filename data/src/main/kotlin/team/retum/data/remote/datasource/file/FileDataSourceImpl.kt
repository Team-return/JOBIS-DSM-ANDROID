package team.retum.data.remote.datasource.file

import okhttp3.MultipartBody
import team.retum.data.remote.api.FileApi
import team.retum.data.remote.request.files.CreatePresignedUrlRequest
import team.retum.data.remote.request.files.CreatePresignedUrlResponse
import team.retum.data.remote.response.file.UploadFileResponse
import team.retum.data.util.HttpHandler
import team.retum.domain.entity.FileType
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val fileApi: FileApi,
) : FileDataSource {
    override suspend fun uploadFile(
        type: FileType,
        files: List<MultipartBody.Part>,
    ): UploadFileResponse = HttpHandler<UploadFileResponse>().httpRequest {
        fileApi.uploadFile(
            type = type,
            file = files,
        )
    }.sendRequest()

    override suspend fun createPresignedUrl(createPresignedUrlRequest: CreatePresignedUrlRequest) =
        HttpHandler<CreatePresignedUrlResponse>().httpRequest {
            fileApi.createPresignedUrl(createPresignedUrlRequest = createPresignedUrlRequest)
        }.sendRequest()
}
