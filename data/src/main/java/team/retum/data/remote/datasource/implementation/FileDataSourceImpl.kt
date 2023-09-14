package team.retum.data.remote.datasource.implementation

import okhttp3.MultipartBody
import team.retum.data.remote.api.file.FileApi
import team.retum.data.remote.datasource.declaration.FileDataSource
import team.retum.data.remote.response.file.UploadFileResponse
import team.retum.data.util.HttpHandler
import team.retum.domain.entity.FileType
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val fileApi: FileApi,
) : FileDataSource {
    override suspend fun uploadFile(
        type: FileType,
        files: List<MultipartBody.Part>
    ): UploadFileResponse = HttpHandler<UploadFileResponse>().httpRequest {
        fileApi.uploadFile(
            type = type,
            file = files,
        )
    }.sendRequest()
}