package team.retum.data.remote.request.application

import team.retum.domain.enums.AttachmentDocsType
import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.param.application.AttachmentsParam

data class ApplyCompanyRequest(
    val attachments: List<Attachments>,
)

data class Attachments(
    val url: String,
    val type: AttachmentDocsType,
)

fun ApplyCompanyParam.toRequest() = ApplyCompanyRequest(
    attachments = this.attachments.map { it.toRequest() },
)

private fun AttachmentsParam.toRequest() = Attachments(
    url = this.url,
    type = this.type,
)