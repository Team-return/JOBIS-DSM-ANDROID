package team.retum.data.remote.request.files

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.FileType
import team.retum.domain.param.files.PresignedUrlParam

data class CreatePresignedUrlRequest(
    @SerializedName("files") val files: List<File>,
) {
    data class File(
        @SerializedName("type") val type: FileType,
        @SerializedName("file_name") val fileName: String,
    )
}

fun PresignedUrlParam.toRequest() = CreatePresignedUrlRequest(
    files = this.files.map { it.toRequest() },
)

private fun PresignedUrlParam.File.toRequest() = CreatePresignedUrlRequest.File(
    type = this.type,
    fileName = this.fileName,
)
