package team.retum.data.remote.request.bug

import com.google.gson.annotations.SerializedName
import team.retum.domain.enums.DevelopmentArea
import team.retum.domain.param.bugreport.ReportBugParam

data class ReportBugRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("development_area") val developmentArea: DevelopmentArea,
    @SerializedName("attachment_urls") val attachmentUrls: List<String>?,
)

fun ReportBugParam.toRequest() = ReportBugRequest(
    title = this.title,
    content = this.content,
    developmentArea = this.developmentArea,
    attachmentUrls = this.attachmentUrls,
)
