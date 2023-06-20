package team.retum.domain.param.application

data class ApplyCompanyParam(
    val attachments: List<AttachmentsParam>,
)

data class AttachmentsParam(
    val url: String,
    val type: AttachmentDocsType,
)

enum class AttachmentDocsType{
    FILE, URL
}