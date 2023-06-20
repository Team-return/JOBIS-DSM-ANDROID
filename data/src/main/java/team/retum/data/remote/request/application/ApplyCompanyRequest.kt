package team.retum.data.remote.request.application

data class ApplyCompanyRequest(
    val url: List<String>,
    val type: AttachmentDocsType,
)

enum class AttachmentDocsType{
    FILE, URL
}