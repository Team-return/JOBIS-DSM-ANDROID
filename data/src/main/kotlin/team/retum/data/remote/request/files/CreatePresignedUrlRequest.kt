package team.retum.data.remote.request.files

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.FileType
import team.retum.domain.param.files.PresignedUrlParam

data class CreatePresignedUrlRequest(
    @SerializedName("type") val type: FileType,
    @SerializedName("file_name") val fileName: String,
)

fun PresignedUrlParam.toRequest() = CreatePresignedUrlRequest(
    type = this.type,
    fileName = this.fileName,
)
