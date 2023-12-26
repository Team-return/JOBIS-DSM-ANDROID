package team.retum.data.remote.request.files

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.files.PresignedUrlEntity

data class CreatePresignedUrlResponse(
    @SerializedName("urls") val urls: List<Urls>,
) {
    data class Urls(
        @SerializedName("file_path") val filePath: String,
        @SerializedName("pre_signed_url") val presignedUrl: String,
    )
}

fun CreatePresignedUrlResponse.toEntity() = PresignedUrlEntity(
    urls = this.urls.map { it.toEntity() },
)

private fun CreatePresignedUrlResponse.Urls.toEntity() = PresignedUrlEntity.Url(
    filePath = this.filePath,
    presignedUrl = this.presignedUrl,
)
