package team.retum.data.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url
import team.retum.data.remote.request.files.CreatePresignedUrlRequest
import team.retum.data.remote.request.files.CreatePresignedUrlResponse
import team.retum.data.remote.response.file.UploadFileResponse
import team.retum.data.remote.url.JobisUrl
import team.retum.domain.entity.FileType

interface FileApi {
    @Multipart
    @POST(JobisUrl.files)
    suspend fun uploadFile(
        @Query("type") type: FileType,
        @Part file: List<MultipartBody.Part>,
    ): UploadFileResponse

    @POST(JobisUrl.Files.presigned)
    suspend fun createPresignedUrl(
        @Body createPresignedUrlRequest: CreatePresignedUrlRequest,
    ): CreatePresignedUrlResponse

    @PUT
    suspend fun uploadFile(
        @Url presignedUrl: String,
        @Body file: RequestBody,
    )
}
