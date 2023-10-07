package team.retum.data.remote.response.file

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.UploadFileEntity

data class UploadFileResponse(
    @SerializedName("urls") val urls: List<String>,
)

fun UploadFileResponse.toEntity() = UploadFileEntity(
    urls = this.urls,
)
