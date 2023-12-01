package team.retum.data.remote.request.application

import com.google.gson.annotations.SerializedName
import team.retum.domain.enums.AttachmentDocsType
import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.param.application.AttachmentsParam

data class ApplyCompanyRequest(
    @SerializedName("attachments") val attachments: List<Attachments>,
)

data class Attachments(
    @SerializedName("url") val url: String,
    @SerializedName("type") val type: AttachmentDocsType,
)

fun ApplyCompanyParam.toRequest() = ApplyCompanyRequest(
    attachments = this.attachments.map { it.toRequest() },
)

private fun AttachmentsParam.toRequest() = Attachments(
    url = this.url,
    type = this.type,
)
