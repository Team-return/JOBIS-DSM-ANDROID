package team.retum.data.remote.response.code

import com.google.gson.annotations.SerializedName
import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.entity.code.CodesEntity

data class FetchCodesResponse(
    @SerializedName("codes") val codes: List<Code>,
)

data class Code(
    @SerializedName("code") val code: Long,
    @SerializedName("keyword") val keyword: String,
)

fun FetchCodesResponse.toEntity() = CodesEntity(
    codes = this.codes.map { it.toEntity() },
)

private fun Code.toEntity() = CodeEntity(
    code = this.code,
    keyword = this.keyword,
)
