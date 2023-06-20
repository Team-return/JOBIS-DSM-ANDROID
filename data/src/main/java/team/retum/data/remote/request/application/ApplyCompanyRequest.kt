package team.retum.data.remote.request.application

import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.param.application.AttachmentDocsType

data class ApplyCompanyRequest(
    val url: List<String>,
    val type: AttachmentDocsType,
)

fun ApplyCompanyParam.toRequest() = ApplyCompanyRequest(
    url = this.url,
    type = this.type,
)