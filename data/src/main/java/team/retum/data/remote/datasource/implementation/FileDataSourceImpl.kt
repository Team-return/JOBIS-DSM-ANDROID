package team.retum.data.remote.datasource.implementation

import okhttp3.MultipartBody
import team.retum.data.remote.api.FileApi
import team.retum.data.remote.api.FileType
import team.retum.data.remote.datasource.declaration.FileDataSource
import team.retum.data.remote.response.file.UploadFileResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val fileApi: FileApi,
) : FileDataSource {
    override suspend fun uploadFile(
        type: FileType,
        file: List<MultipartBody.Part>
    ): UploadFileResponse = HttpHandler<UploadFileResponse>().httpRequest {
        fileApi.uploadFile(
            type = type,
            file = file,
        )
    }.sendRequest()
}