package team.retum.data.repository

import android.os.Build
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import team.retum.data.remote.datasource.file.FileDataSource
import team.retum.data.remote.request.RequestType
import team.retum.data.remote.request.files.toEntity
import team.retum.data.remote.request.files.toRequest
import team.retum.data.remote.response.file.toEntity
import team.retum.domain.entity.FileType
import team.retum.domain.entity.UploadFileEntity
import team.retum.domain.param.files.PresignedUrlParam
import team.retum.domain.repository.FileRepository
import java.io.File
import java.nio.file.Files
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
) : FileRepository {
    override suspend fun uploadFile(
        type: FileType,
        files: List<File>,
    ): UploadFileEntity = fileDataSource.uploadFile(
        type = type,
        files = files.map { it.getImageMultipart() },
    ).toEntity()

    override suspend fun createPresignedUrl(presignedUrlParam: PresignedUrlParam) =
        fileDataSource.createPresignedUrl(
            createPresignedUrlRequest = presignedUrlParam.toRequest(),
        ).toEntity()

    override suspend fun uploadFile(
        presignedUrl: String,
        file: File,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fileDataSource.uploadFile(
                presignedUrl = presignedUrl,
                file = Files.readAllBytes(file.toPath()).toRequestBody(
                    contentType = RequestType.Binary.toMediaTypeOrNull(),
                ),
            )
        }
    }
}

private fun File.getImageMultipart(): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        name = "file",
        filename = name,
        body = asRequestBody(RequestType.Multipart.toMediaType()),
    )
