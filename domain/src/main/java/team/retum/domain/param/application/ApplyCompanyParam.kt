package team.retum.domain.param.application

data class ApplyCompanyParam(
    val url: List<String>,
    val type: AttachmentDocsType,
)

enum class AttachmentDocsType{
    FILE, URL
}