package team.retum.data.remote.response.file

import com.google.gson.annotations.SerializedName

data class UploadFileResponse(
    @SerializedName("urls") val urls: List<String>,
)