package team.retum.data.remote.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
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
}

