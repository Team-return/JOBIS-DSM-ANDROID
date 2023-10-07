package team.retum.domain.param.application

import team.retum.domain.enums.AttachmentDocsType

data class ApplyCompanyParam(
    val attachments: List<AttachmentsParam>,
)

data class AttachmentsParam(
    val url: String,
    val type: AttachmentDocsType,
)
