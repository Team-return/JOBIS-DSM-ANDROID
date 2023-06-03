package team.retum.data.remote.response.code

import com.google.gson.annotations.SerializedName

data class FetchCodesResponse(
    @SerializedName("codes") val codes: List<Code>,
)

data class Code(
    @SerializedName("code") val code: Long,
    @SerializedName("keyword") val keyword: String,
)
